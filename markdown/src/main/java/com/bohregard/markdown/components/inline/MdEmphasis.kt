package com.bohregard.markdown.components.inline

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.node.Emphasis

@Composable
internal fun MdEmphasis(annotatedStringBuilder: AnnotatedString.Builder, emphasis: Emphasis) {
    val textStyle = LocalMarkdownTextStyle.current
    val italicStyle = textStyle.copy(
        fontStyle = FontStyle.Italic,
    ).toSpanStyle()
    annotatedStringBuilder.withStyle(italicStyle) {
        TextParser(node = emphasis, annotatedStringBuilder = annotatedStringBuilder)
    }
}