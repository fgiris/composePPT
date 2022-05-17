package com.fatihgiris.composePPT.foundation.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import com.fatihgiris.composePPT.ComposePPTNodeApplier
import com.fatihgiris.composePPT.node.TextNode

/**
 * A core element to display the text.
 *
 * @param text The text to be displayed.
 * @param style Style configuration for the text such as font size and color.
 */
@Composable
fun Text(
    text: String,
    style: TextStyle = LocalTextStyle.current
) {
    ComposeNode<TextNode, ComposePPTNodeApplier>(
        factory = ::TextNode
    ) {
        set(text) { this.text = it }
        set(style) { this.style = it }
    }
}

/**
 * Composition local for the text style to be used in [Text] composable by default.
 */
val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { DefaultTextStyle }