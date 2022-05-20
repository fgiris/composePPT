package com.fatihgiris.composePPT

import androidx.compose.runtime.*
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas
import com.fatihgiris.composePPT.graphics.ComposePPTDisplay
import com.fatihgiris.composePPT.node.ComposePPTNode
import com.fatihgiris.composePPT.node.PresentationNode
import com.fatihgiris.composePPT.node.setContent
import kotlinx.coroutines.*

/**
 * Creates a presentation with the [presentationFileName] from the [content].
 *
 * @param content The content of the presentation. This block should only contain
 * the composables defined in composePPT.
 *
 * @param presentationFileName The file name of the presentation without a file extension
 */
fun runComposePPT(
    presentationFileName: String = "composePPT",
    content: @Composable () -> Unit
) = runBlocking {
    val frameClock = BroadcastFrameClock()

    // This context is used to run Recomposer. Parenting the job to the runBlocking
    // in order to be able to cancel during the cancellation of all children.
    // Do not use this context to launch any coroutine since this context is considered
    // only used by Recomposer.
    val effectCoroutineContext = Job(coroutineContext[Job]) + frameClock

    // Create a root node to be given to applier during the composition creation
    val rootNode = PresentationNode()

    launch {
        // This wires up the callbacks needed for the state object changes. Without this
        // there will be no recomposition if any of the state object changes.
        SnapshotManager.ensureStarted()
    }

    val recomposer = Recomposer(effectCoroutineContext)
    val composition = rootNode.setContent(recomposer, content)

    // Starting undispatched in order not to send a frame before the recomposer
    // registers an awaiter.
    launch(context = frameClock, start = CoroutineStart.UNDISPATCHED) {
        recomposer.runRecomposeAndApplyChanges()
    }

    launch {
        frameClock.dispatchFrame(
            refreshRateMillis = 1000L,
            timeNanos = {
                System.nanoTime()
            },
            frameCallback = {
                display(rootNode, presentationFileName)

                // This is the job used as a parent for effects in the recomposer. See: Recomposer.effectJob
                val recomposerEffectJob = effectCoroutineContext.job.children.first()

                // Whether there is any ongoing job whose parent is effect job. (e.g LaunchedEffect etc.)
                val hasUncompletedJob = recomposerEffectJob.children.any { !it.isCompleted }

                if (!hasUncompletedJob) {
                    // Even though all the effect jobs are completed, there might be some snapshot
                    // invalidations which the recomposer might not have been notified. In other words,
                    // if a state object is updated, wait for the recomposer to get notified.
                    // TODO: Find a better way to deal with this race condition
                    //  Might be related: https://issuetracker.google.com/issues/169425431
                    delay(500)

                    // One last frame sending to recompose it for the last time
                    frameClock.sendFrame(System.nanoTime())

                    // Display the last frame
                    display(rootNode, presentationFileName)

                    // This will dispose the composition and send forget events to
                    // remember observers (i.e DisposableEffect)
                    composition.dispose()
                    recomposer.close()

                    // Cancel all child coroutines started from this runBlocking in order to
                    // end the process
                    this@runBlocking.coroutineContext.cancelChildren()
                }
            }
        )
    }
}

/**
 * Sends a frame to the clock with a given [refreshRateMillis] interval
 * while sending the time nanos from [timeNanos] and executes
 * [frameCallback] afterwards.
 */
internal suspend fun BroadcastFrameClock.dispatchFrame(
    refreshRateMillis: Long,
    timeNanos: () -> Long,
    frameCallback: suspend () -> Unit = {}
) {
    while (true) {
        sendFrame(timeNanos())
        frameCallback()

        // Refresh rate for each frame
        delay(refreshRateMillis)
    }
}

/**
 * Renders and draws the content created for [rootNode] on a display to an exported file
 * named [presentationFileName],
 */
private fun display(
    rootNode: ComposePPTNode,
    presentationFileName: String
) {
    val composePPTDisplay = ComposePPTDisplay(presentationFileName)
    val canvas = ComposePPTCanvas()

    composePPTDisplay.display(rootNode.render(canvas))
}