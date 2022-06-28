package com.bohregard.markdown.components.inline

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.node.Link

@Composable
internal fun MdLink(annotatedStringBuilder: AnnotatedString.Builder, link: Link) {
    val textStyle = LocalMarkdownTextStyle.current
    val linkStyle = textStyle.copy(
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
    )
    annotatedStringBuilder.pushStyle(linkStyle)
    annotatedStringBuilder.pushStringAnnotation("URL", link.destination)
    TextParser(node = link, annotatedStringBuilder = annotatedStringBuilder)
    annotatedStringBuilder.pop()
    annotatedStringBuilder.pop()
}