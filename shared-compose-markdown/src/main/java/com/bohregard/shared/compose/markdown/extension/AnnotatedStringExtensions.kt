package com.bohregard.shared.compose.markdown.extension

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import com.bohregard.shared.compose.markdown.LocalMarkdownBoldStyle
import com.bohregard.shared.compose.markdown.LocalMarkdownTextStyle
import com.bohregard.shared.compose.markdown.components.MDImage
import org.commonmark.node.*

@Composable
internal fun AnnotatedString.Builder.AppendMarkdownChildren(parent: Node) {
    val textStyle = LocalMarkdownTextStyle.current
    val boldStyle = LocalMarkdownBoldStyle.current
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is Paragraph -> AppendMarkdownChildren(child)
            is Emphasis -> {
                pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                AppendMarkdownChildren(child)
                pop()
            }
            is StrongEmphasis -> {
                pushStyle(boldStyle)
                AppendMarkdownChildren(child)
                pop()
            }
            is Link -> {
                val underline = SpanStyle(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                )
                pushStyle(underline)
                pushStringAnnotation("URL", child.destination)
                AppendMarkdownChildren(child)
                pop()
                pop()
            }
            is Text -> {
                pushStyle(textStyle)
                append(child.literal)
                pop()
            }
            is HardLineBreak -> {
                append("\n")
            }
            is Image -> {
                MDImage(child)
            }
            else -> println("Annotated Child: $child")
        }
        child = child.next
    }
}