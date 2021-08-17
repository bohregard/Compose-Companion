package com.bohregard.shared.compose.markdown.extension

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import org.commonmark.node.*

@Composable
fun AnnotatedString.Builder.AppendMarkdownChildren(parent: Node) {
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
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
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
                append(child.literal)
            }
            is HardLineBreak -> {
                append("\n")
            }
            else -> println(child)
        }
        child = child.next
    }
}