package com.fatihgiris.composePPT.foundation.slide

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.fatihgiris.composePPT.ComposePPTNodeApplier
import com.fatihgiris.composePPT.node.PresentationNode

/**
 * A root level element to create a presentation. This composable should
 * only have [Slide] composables as a direct child.
 */
@Composable
internal fun Presentation(
    content: @Composable () -> Unit
) {
    ComposeNode<PresentationNode, ComposePPTNodeApplier>(
        factory = ::PresentationNode,
        update = {},
        content = content
    )
}