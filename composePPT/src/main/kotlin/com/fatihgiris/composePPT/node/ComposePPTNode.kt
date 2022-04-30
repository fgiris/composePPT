package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTNodeApplier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A base node to represent any node in composePPT in slot table.
 */
abstract class ComposePPTNode {
    val children = mutableListOf<ComposePPTNode>()

    /**
     * Renders the current node to the [canvas] and returns the content.
     */
    abstract fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent
}

/**
 * Starts the composition from the [ComposePPTNode].
 */
fun ComposePPTNode.setContent(
    parent: CompositionContext,
    content: @Composable () -> Unit
): Composition {
    return Composition(ComposePPTNodeApplier(this), parent).apply {
        setContent(content)
    }
}