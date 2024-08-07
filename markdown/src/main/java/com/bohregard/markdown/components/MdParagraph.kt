package com.bohregard.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.node.Paragraph

@Composable
internal fun MdParagraph(paragraph: Paragraph) {
    val style = LocalMarkdownTextStyle.current
    // TODO Peek the children for images?
    val annotatedStringBuilder = AnnotatedString.Builder()
    annotatedStringBuilder.withStyle(style.toSpanStyle()) {
        TextParser(node = paragraph, annotatedStringBuilder = annotatedStringBuilder)
    }
    MdClickableText(annotatedStringBuilder = annotatedStringBuilder)
}