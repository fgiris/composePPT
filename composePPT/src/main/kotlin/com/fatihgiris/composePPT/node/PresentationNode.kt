package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A presentation element in the tree built with composePPT. [PresentationNode] can
 * only have [SlideNode] nodes.
 */
internal class PresentationNode : ComposePPTNode() {
    override fun layout() {
        children.forEach {
            require(it is SlideNode) {
                "Presentation node should only have a slide node as its direct child."
            }

            it.layout()
        }
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        layout()

        return ComposePPTCanvasContent.PresentationContent(
            slides = children.map {
                it.render(canvas) as ComposePPTCanvasContent.SlideContent
            }
        )
    }
}