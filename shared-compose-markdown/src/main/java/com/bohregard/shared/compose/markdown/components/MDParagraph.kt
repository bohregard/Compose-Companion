package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import com.bohregard.shared.compose.markdown.LocalMarkdownTextStyle
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.Paragraph

@Composable
internal fun MDParagraph(paragraph: Paragraph) {
    val textStyle = LocalMarkdownTextStyle.current
    Box {
        val text = buildAnnotatedString {
            pushStyle(textStyle)
            AppendMarkdownChildren(paragraph)
            pop()
        }
        MDClickableText(text = text)
    }
}