package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.TextCanvas

/**
 * A text element in the tree built with composePPT.
 */
class TextNode : ComposePPTNode() {
    var text: String = ""

    override fun render(canvas: TextCanvas): ComposePPTCanvasContent {
        canvas.writeText(text)
        return canvas.render()
    }
}