package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.foundation.text.TextStyle

/**
 * A canvas for rendering the content onto.
 */
interface Canvas {

    /**
     * Renders and returns the content.
     */
    fun render(): ComposePPTCanvasContent

    /**
     * Draws the [text] to the [positionIndex] with a given [style]
     * in the content list on the canvas.
     */
    fun drawText(
        text: String,
        positionIndex: Int,
        style: TextStyle
    )
}

/**
 * A canvas which renders [ComposePPTCanvasContent]. This canvas only supports
 * drawing based on index as a position and has an internal [content] which
 * keeps the [ComposePPTCanvasContent] and the position index for each element.
 */
class ComposePPTCanvas : Canvas {
    // Keeps the content for the canvas as (position, element)
    private val content = hashMapOf<Int, ComposePPTCanvasContent>()

    override fun drawText(
        text: String,
        positionIndex: Int,
        style: TextStyle
    ) {
        content[positionIndex] = ComposePPTCanvasContent.TextContent(text, style)
    }

    override fun render(): ComposePPTCanvasContent {
        val canvasContent = content.toSortedMap().values.toList()

        // TODO: The root node is already set as list in runComposePPT.
        return ComposePPTCanvasContent.ListContent(contentList = canvasContent)
    }
}