package com.fatihgiris.composePPT

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.withRunningRecomposer
import com.fatihgiris.composePPT.graphics.LogcatDisplay
import com.fatihgiris.composePPT.graphics.TextCanvas
import com.fatihgiris.composePPT.node.ComposePPTNode
import com.fatihgiris.composePPT.node.ListNode
import com.fatihgiris.composePPT.node.setContent
import kotlinx.coroutines.*

fun runComposePPT(content: @Composable () -> Unit) = runBlocking {
    val frameClock = BroadcastFrameClock()
    val effectCoroutineContext = Job(coroutineContext[Job]) + frameClock
    val compositionScope = CoroutineScope(effectCoroutineContext)

    // Create a root node to be given to applier during the composition creation
    val rootNode = ListNode()

    compositionScope.launch {
        launch {
            // This wires up the callbacks needed for the state object changes. Without this
            // there will be no recomposition if any of the state object changes.
            SnapshotManager.ensureStarted()
        }

        withRunningRecomposer { recomposer ->
            // TODO: Dispose the composition when the app is closed OR no running job
            //  such as a job inside LaunchEffect
            val composition = rootNode.setContent(recomposer, content)

            // Launching a new coroutine inside the withRunningRecomposer lambda since it will
            // wait all jobs to be finished inside this lambda to close the recomposer
            launch { frameClock.dispatchFrame(1000L) }

            display(rootNode)
        }
    }
}

/**
 * Sends a frame to the clock with a given [refreshRateMillis] interval.
 */
private suspend fun BroadcastFrameClock.dispatchFrame(refreshRateMillis: Long) {
    while (true) {
        // Time nanos is only being used with animations in compose UI. Ignore it for now.
        sendFrame(0L)

        // Refresh rate for each frame
        delay(refreshRateMillis)
    }
}

/**
 * Renders and draws the content created for [rootNode] on a display.
 */
private fun display(rootNode: ComposePPTNode) {
    val display = LogcatDisplay()
    val canvas = TextCanvas()

    display.draw(rootNode.render(canvas))
}