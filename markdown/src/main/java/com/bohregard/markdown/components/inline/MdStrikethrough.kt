package com.bohregard.markdown.components.inline

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.ext.gfm.strikethrough.Strikethrough

@Composable
internal fun MdStrikethrough(annotatedStringBuilder: AnnotatedString.Builder, strikethrough: Strikethrough) {
    val textStyle = LocalMarkdownTextStyle.current
    val strikethroughStyle = textStyle.copy(
        textDecoration = TextDecoration.LineThrough
    )
    annotatedStringBuilder.withStyle(strikethroughStyle) {
        TextParser(node = strikethrough, annotatedStringBuilder = annotatedStringBuilder)
    }
}