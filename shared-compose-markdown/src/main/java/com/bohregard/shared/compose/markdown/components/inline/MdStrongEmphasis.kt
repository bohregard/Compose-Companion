package com.bohregard.shared.compose.markdown.components.inline

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.bohregard.shared.compose.markdown.LocalMarkdownTextStyle
import com.bohregard.shared.compose.markdown.util.TextParser
import org.commonmark.node.StrongEmphasis

@Composable
internal fun MdStrongEmphasis(annotatedStringBuilder: AnnotatedString.Builder, strongEmphasis: StrongEmphasis) {
    val textStyle = LocalMarkdownTextStyle.current
    val boldStyle = textStyle.copy(
        fontWeight = FontWeight.Bold
    )
    annotatedStringBuilder.withStyle(boldStyle) {
        TextParser(node = strongEmphasis, annotatedStringBuilder = annotatedStringBuilder)
    }
}