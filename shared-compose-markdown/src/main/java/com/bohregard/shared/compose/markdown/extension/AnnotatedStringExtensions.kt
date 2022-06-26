package com.bohregard.shared.compose.markdown.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.bohregard.shared.compose.markdown.LocalMarkdownTextStyle
import com.bohregard.shared.compose.markdown.components.MDBlockQuote
import com.bohregard.shared.compose.markdown.components.MDImage
import org.commonmark.node.*

@Composable
internal fun AnnotatedString.Builder.AppendMarkdownChildren(parent: Node) {
    val textStyle = LocalMarkdownTextStyle.current
    val boldStyle = textStyle.copy(
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold
    )
    val italicStyle = textStyle.copy(
        fontStyle = FontStyle.Italic,
    )
    val linkStyle = textStyle.copy(
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
    )
    var child = parent.firstChild
    var count = if (parent is OrderedList) parent.startNumber - 1 else 0

    while (child != null) {
        if (child is ListItem) {
            count++
        }
        when (child) {
            is BlockQuote -> {
                // Block quote part deux???
                MDBlockQuote(blockQuote = child)
            }
            is Paragraph -> {
                AppendMarkdownChildren(child)
            }
            is Emphasis -> {
                pushStyle(italicStyle)
                AppendMarkdownChildren(child)
                pop()
            }
            is StrongEmphasis -> {
                pushStyle(boldStyle)
                AppendMarkdownChildren(child)
                pop()
            }
            is ListItem -> {
                pushStyle(textStyle)
                when (child.parent) {
                    is BulletList -> append("• ")
                    is OrderedList -> {
                        val orderedList = child.parent as OrderedList
                        append("$count${orderedList.delimiter} ")
                    }
                }
                pop()
                AppendMarkdownChildren(child)
                if (child.next != null) {
                    append("\n")
                }
            }
            is Link -> {
                pushStyle(linkStyle)
                pushStringAnnotation("URL", child.destination)
                AppendMarkdownChildren(child)
                pop()
                pop()
            }
            is Text -> append(child.literal)
            is SoftLineBreak,
            is HardLineBreak -> {
                append("\n")
            }
            is Image -> {
                MDImage(child)
            }
            else -> println("Annotated Child Else: $child")
        }
        child = child.next
    }
}