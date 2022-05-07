package com.fatihgiris.composePPT.foundation.slide

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.fatihgiris.composePPT.ComposePPTNodeApplier
import com.fatihgiris.composePPT.node.SlideNode

/**
 * A core element to display a slide.
 */
@Composable
fun Slide(
    content: @Composable () -> Unit
) {
    ComposeNode<SlideNode, ComposePPTNodeApplier>(
        factory = ::SlideNode,
        update = {},
        content = content
    )
}