package com.fatihgiris.composePPT

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import com.fatihgiris.composePPT.graphics.LogcatDisplay
import com.fatihgiris.composePPT.graphics.TextCanvas
import com.fatihgiris.composePPT.node.ListNode
import com.fatihgiris.composePPT.node.setContent
import kotlinx.coroutines.*

fun runComposePPT(content: @Composable () -> Unit) = runBlocking {
    val frameClock = BroadcastFrameClock()

    val compositionScope = CoroutineScope(Job(coroutineContext[Job]) + frameClock)

    compositionScope.launch {
        // This wires up the callbacks needed for the state object changes. Without this
        // there will be no recomposition if any of the state object changes.
        SnapshotManager.ensureStarted()
    }

    val recomposer = Recomposer(compositionScope.coroutineContext)

    // Create a root node to be given to applier during the composition creation
    val rootNode = ListNode()

    // TODO: Dispose the composition & recomposer when the app is closed
    val composition = rootNode.setContent(recomposer, content)

    compositionScope.launch {
        recomposer.runRecomposeAndApplyChanges()

        while (true) {
            // Time nanos is only being used with animations in compose UI. Ignore it for now.
            frameClock.sendFrame(0L)

            // Refresh rate for each frame
            delay(16)
        }
    }

    val display = LogcatDisplay()
    val canvas = TextCanvas()

    display.draw(rootNode.render(canvas))
}