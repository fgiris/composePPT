package com.fatihgiris.composePPT.graphics

import com.fatihgiris.composePPT.ComposePPTCanvasContent

/**
 * A display which knows how to translate & draw [ComposePPTCanvasContent]. For instance,
 * if the display only supports displaying texts then it can translate the content to
 * text and draw it.
 */
internal interface ComposePPTDisplay {

    /**
     * Translates and draws the given [content] to the display unit.
     */
    fun draw(content: ComposePPTCanvasContent)
}

/**
 * A logcat display to draw the [ComposePPTCanvasContent]. This display can be used for
 * debugging purposes.
 */
class LogcatDisplay : ComposePPTDisplay {

    override fun draw(content: ComposePPTCanvasContent) {
        when (content) {
            is ComposePPTCanvasContent.TextContent -> {
                drawText(content)
            }
            is ComposePPTCanvasContent.ListContent -> {
                content.contentList.forEach {
                    draw(it)
                }
            }
        }
    }

    private fun drawText(textContent: ComposePPTCanvasContent.TextContent) {
        println(textContent.text)
    }
}