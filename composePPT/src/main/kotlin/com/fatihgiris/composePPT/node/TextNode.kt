package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A text element in the tree built with composePPT.
 */
class TextNode : ComposePPTNode() {
    var text: String = ""

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        canvas.writeText(text)
        return canvas.render()
    }
}