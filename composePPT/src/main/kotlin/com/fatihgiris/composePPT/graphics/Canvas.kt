package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent

/**
 * A canvas for rendering the content onto.
 */
interface Canvas {

    /**
     * Renders and returns the content.
     */
    fun render(): ComposePPTCanvasContent
}

/**
 * A canvas which only renders [ComposePPTCanvasContent].
 */
class ComposePPTCanvas : Canvas {
    private var text: String = ""

    fun writeText(text: String) {
        this.text = text
    }

    override fun render(): ComposePPTCanvasContent {
        return ComposePPTCanvasContent.TextContent(text)
    }
}