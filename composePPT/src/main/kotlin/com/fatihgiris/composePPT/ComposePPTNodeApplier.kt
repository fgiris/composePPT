package com.fatihgiris.composePPT

import androidx.compose.runtime.AbstractApplier
import com.fatihgiris.composePPT.node.ComposePPTNode

/**
 * An applier to teach the compose how to manage a tree of [ComposePPTNode] nodes.
 */
class ComposePPTNodeApplier(root: ComposePPTNode) : AbstractApplier<ComposePPTNode>(root) {
    override fun insertTopDown(index: Int, instance: ComposePPTNode) {
        current.children.add(index, instance)
    }

    override fun insertBottomUp(index: Int, instance: ComposePPTNode) {
        // Ignored as the tree is built top-down.
    }

    override fun remove(index: Int, count: Int) {
        current.children.remove(index, count)
    }

    override fun move(from: Int, to: Int, count: Int) {
        current.children.move(from, to, count)
    }

    override fun onClear() {
        root.children.clear()
    }
}