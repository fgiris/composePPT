package com.fatihgiris.composePPT.foundation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import com.fatihgiris.composePPT.ComposePPTNodeApplier
import com.fatihgiris.composePPT.node.ListNode

@Composable
fun List(content: @Composable () -> Unit) {
    ComposeNode<ListNode, ComposePPTNodeApplier>(
        factory = ::ListNode,
        update = {},
        content = content
    )
}