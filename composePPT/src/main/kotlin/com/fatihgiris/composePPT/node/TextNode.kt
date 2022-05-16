package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.foundation.text.DefaultTextStyle
import com.fatihgiris.composePPT.foundation.text.TextStyle
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A text element in the tree built with composePPT.
 */
class TextNode : ComposePPTNode() {
    var text: String = ""
    var style: TextStyle = DefaultTextStyle

    override var positionIndex: Int = 0

    override fun layout() {
        // No child node. Parent will adjust the position index.
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        canvas.drawText(
            text,
            positionIndex,
            style
        )

        return canvas.render()
    }
}