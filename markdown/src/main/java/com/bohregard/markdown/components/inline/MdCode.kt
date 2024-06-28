package com.bohregard.markdown.components.inline

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.node.Code

@Composable
internal fun MdCode(annotatedStringBuilder: AnnotatedString.Builder, code: Code) {
    val textStyle = LocalMarkdownTextStyle.current
    val codeStyle = textStyle.copy(
        color = MaterialTheme.colorScheme.background,
        fontFamily = FontFamily.Monospace,
        background = MaterialTheme.colorScheme.onBackground,
    ).toSpanStyle()

    annotatedStringBuilder.pushStyle(codeStyle)
    annotatedStringBuilder.append(" ${code.literal} ")
    annotatedStringBuilder.pop()
    TextParser(node = code, annotatedStringBuilder = annotatedStringBuilder)
}