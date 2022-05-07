package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A slide element in the tree built with composePPT. [SlideNode] can only have
 * one child node which should *not* be another slide node.
 */
class SlideNode : ComposePPTNode() {
    override fun layout() {
        require(children.size == 1) {
            "Slide node can only have a single child."
        }

        require(children[0] !is SlideNode) {
            "Slide node cannot have another slide node as a child."
        }
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        layout()
        return ComposePPTCanvasContent.SlideContent(
            content = children[0].render(canvas)
        )
    }
}