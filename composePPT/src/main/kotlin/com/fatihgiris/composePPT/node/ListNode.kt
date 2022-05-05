package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.ComposePPTCanvas

/**
 * A list element in the tree built with composePPT.
 */
class ListNode : ComposePPTNode() {

    override fun layout() {
        var currentPositionIndex = positionIndex

        children.forEach {
            it.layout()
            it.positionIndex = currentPositionIndex++
        }
    }

    override fun render(canvas: ComposePPTCanvas): ComposePPTCanvasContent {
        val contentList = mutableListOf<ComposePPTCanvasContent>()

        // Iterate through all children and make them render themselves
        children.forEach {
            contentList.add(it.render(canvas))
        }

        return ComposePPTCanvasContent.ListContent(contentList)
    }
}