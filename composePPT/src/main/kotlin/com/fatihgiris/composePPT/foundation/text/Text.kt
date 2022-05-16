package com.fatihgiris.composePPT.foundation.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.fatihgiris.composePPT.ComposePPTNodeApplier
import com.fatihgiris.composePPT.node.TextNode

/**
 * A core element to display the text.
 *
 * @param text The text to be displayed.
 */
@Composable
fun Text(
    text: String,
    style: TextStyle = DefaultTextStyle
) {
    ComposeNode<TextNode, ComposePPTNodeApplier>(
        factory = ::TextNode
    ) {
        set(text) { this.text = it }
        set(style) { this.style = it }
    }
}