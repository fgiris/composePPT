package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent

/**
 * A canvas for rendering the content onto.
 */
interface ComposePPTCanvas {

    /**
     * Renders and returns the content.
     */
    fun render(): ComposePPTCanvasContent
}

/**
 * A text canvas which only renders [ComposePPTCanvasContent.TextContent].
 */
class TextCanvas : ComposePPTCanvas {
    private var text: String = ""

    fun writeText(text: String) {
        this.text = text
    }

    override fun render(): ComposePPTCanvasContent {
        return ComposePPTCanvasContent.TextContent(text)
    }
}