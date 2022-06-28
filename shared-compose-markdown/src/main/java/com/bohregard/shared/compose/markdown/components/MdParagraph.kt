package com.bohregard.shared.compose.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import com.bohregard.shared.compose.markdown.util.TextParser
import org.commonmark.node.Paragraph

@Composable
internal fun MdParagraph(paragraph: Paragraph) {
    // TODO Peek the children for images?
    val annotatedStringBuilder = AnnotatedString.Builder()
    TextParser(node = paragraph, annotatedStringBuilder = annotatedStringBuilder)
    MdClickableText(text = annotatedStringBuilder.toAnnotatedString())
}