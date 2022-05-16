package com.fatihgiris.composePPT

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.withRunningRecomposer
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
    // in order to be able to cancel during the cancellation of all children
    val effectCoroutineContext = Job(coroutineContext[Job]) + frameClock

    // Create a root node to be given to applier during the composition creation
    val rootNode = PresentationNode()

    lateinit var composition: Composition

    launch {
        // This wires up the callbacks needed for the state object changes. Without this
        // there will be no recomposition if any of the state object changes.
        SnapshotManager.ensureStarted()
    }

    // Launching with effect coroutine context since this context will be used to
    // check & cancel depending on if there is any job ongoing with composition.
    launch(effectCoroutineContext) {
        // Start the initial composition
        withRunningRecomposer { recomposer ->
            composition = rootNode.setContent(recomposer, content)
        }
    }

    launch {
        frameClock.dispatchFrame(
            refreshRateMillis = 500L,
            timeNanos = {
                System.nanoTime()
            },
            frameCallback = {
                display(rootNode, presentationFileName)

                val hasUncompletedJob = effectCoroutineContext.job.children.any { !it.isCompleted }

                if (!hasUncompletedJob) {
                    // This will dispose the composition and send forget events to
                    // remember observers (i.e DisposableEffect)
                    composition.dispose()

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
    frameCallback: () -> Unit = {}
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