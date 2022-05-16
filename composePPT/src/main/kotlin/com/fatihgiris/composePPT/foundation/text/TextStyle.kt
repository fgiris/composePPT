package com.fatihgiris.composePPT.foundation.text

import java.awt.Color

private const val DefaultFontSize = 20f
private val DefaultFontColor = Color.BLACK

/**
 * Several style attributes to be applied to [TextNode] when drawing
 * to the canvas.
 */
data class TextStyle(
    val fontSize: Float = DefaultFontSize,
    val fontColor: Color = DefaultFontColor
)

internal val DefaultTextStyle = TextStyle(
    fontSize = DefaultFontSize,
    fontColor = DefaultFontColor
)