package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.OrderedList

@Composable
internal fun MDOrderedList(orderedList: OrderedList) {
    Column {
        val text = buildAnnotatedString {
            AppendMarkdownChildren(orderedList)
        }
        MDClickableText(text = text)
    }
}