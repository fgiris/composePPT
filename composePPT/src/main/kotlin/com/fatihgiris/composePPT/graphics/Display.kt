package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent

/**
 * A display which knows how to translate & draw [ComposePPTCanvasContent]. For instance,
 * if the display only supports displaying texts then it can translate the content to
 * text and draw it.
 */
internal interface Display {

    /**
     * Translates and draws the given [content] to the display unit.
     */
    fun display(content: ComposePPTCanvasContent)
}

/**
 * A logcat display to draw the [ComposePPTCanvasContent]. This display can be used for
 * debugging purposes.
 */
class LogcatDisplay : Display {

    override fun display(content: ComposePPTCanvasContent) {
        when (content) {
            is ComposePPTCanvasContent.TextContent -> {
                doDisplay(content)
            }
            is ComposePPTCanvasContent.ListContent -> {
                content.contentList.forEach {
                    display(it)
                }
            }
        }
    }

    private fun doDisplay(textContent: ComposePPTCanvasContent.TextContent) {
        println(textContent.text)
    }
}