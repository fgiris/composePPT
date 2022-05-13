package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A slide element in the tree built with composePPT. [SlideNode] can only have
 * one child node which should *not* be another slide node.
 */
class SlideNode(
    private val slideType: SlideType
) : ComposePPTNode() {

    var title: String = ""

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
        val content = children[0].render(canvas)

        return when (slideType) {
            SlideType.TITLE_AND_BODY -> ComposePPTCanvasContent.SlideContent.TitleAndBodyContent(
                title = title,
                content = content
            )
            SlideType.ONLY_BODY -> ComposePPTCanvasContent.SlideContent.OnlyBodyContent(
                content = content
            )
        }
    }

    enum class SlideType {
        TITLE_AND_BODY, ONLY_BODY
    }
}