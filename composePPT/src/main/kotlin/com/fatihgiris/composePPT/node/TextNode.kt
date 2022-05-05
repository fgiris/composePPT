package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A text element in the tree built with composePPT.
 */
class TextNode : ComposePPTNode() {
    var text: String = ""

    override var positionIndex: Int = 0

    override fun layout() {
        // No child node. Parent will adjust the position index.
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        canvas.drawText(text, positionIndex)
        return canvas.render()
    }
}