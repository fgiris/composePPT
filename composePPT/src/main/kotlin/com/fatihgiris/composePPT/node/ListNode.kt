package com.fatihgiris.composePPT.node

import com.fatihgiris.composePPT.ComposePPTCanvasContent
import com.fatihgiris.composePPT.graphics.TextCanvas

/**
 * A list element in the tree built with composePPT.
 */
class ListNode : ComposePPTNode() {
    override fun render(canvas: TextCanvas): ComposePPTCanvasContent {
        val contentList = mutableListOf<ComposePPTCanvasContent>()

        // Iterate through all children and make them render themselves
        children.forEach {
            contentList.add(it.render(canvas))
        }

        return ComposePPTCanvasContent.ListContent(contentList)
    }
}