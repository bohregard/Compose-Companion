package com.bohregard.shared.compose.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.SpanStyle
import com.bohregard.shared.compose.markdown.components.MdBlockChildren
import com.bohregard.shared.compose.markdown.model.MarkdownConfiguration
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser

@Composable
fun MarkdownText(
    markdown: String,
    configuration: MarkdownConfiguration = MarkdownConfiguration(),
    textStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onSurface
    ),
) {
    CompositionLocalProvider(
        LocalMarkdownTextStyle provides textStyle,
        LocalMarkdownConfiguration provides configuration
    ) {
        val parser = Parser.builder()
            .extensions(mutableListOf(TablesExtension.create()))
            .build()
        val document = parser.parse(markdown.replace("^(#+)([^\\s#].*)\$".toRegex(), "$1 $2"))
        printDocument(document)
        Column {
            MdBlockChildren(parent = document)
        }
    }
}

internal fun printDocument(document: Node, depth: Int = 0) {
    var child = document.firstChild
    while (child != null) {
        println("${"-".repeat(depth + 1)}> $child")
        printDocument(child, depth + 1)
        child = child.next
    }
}
//
//@Composable
//fun textParser(node: Node, annotation: AnnotatedString.Builder) {
//
//    val textStyle = LocalMarkdownTextStyle.current
//    val boldStyle = textStyle.copy(
//        fontWeight = FontWeight.Bold
//    )
//    val italicStyle = textStyle.copy(
//        fontStyle = FontStyle.Italic,
//    )
//    val linkStyle = textStyle.copy(
//        color = MaterialTheme.colorScheme.primary,
//        textDecoration = TextDecoration.Underline
//    )
//    val codeStyle = textStyle.copy(
//        color = MaterialTheme.colorScheme.background,
//        fontFamily = FontFamily.Monospace,
//        background = MaterialTheme.colorScheme.onBackground,
//    )
//
//    var count = if (node is OrderedList) node.startNumber - 1 else 0
//    var child = node.firstChild
//    while (child != null) {
//        when (child) {
//            is Image -> {
//                AsyncImage(model = child.destination, contentDescription = null)
//            }
//            is Code -> {
//                annotation.pushStyle(codeStyle)
//                annotation.append(" ${child.literal} ")
//                annotation.pop()
//                textParser(node = child, annotation = annotation)
//            }
//            is ListItem -> {
//                when (child.parent) {
//                    is BulletList -> {
//                        annotation.append("• ")
//                    }
//                    is OrderedList -> {
//                        count++
//                        val orderedList = child.parent as OrderedList
//                        annotation.append("$count${orderedList.delimiter} ")
//                    }
//                }
//                textParser(node = child, annotation = annotation)
//                annotation.append("\n")
//            }
//            is Emphasis -> {
//                annotation.withStyle(italicStyle) {
//                    textParser(node = child, annotation = annotation)
//                }
//            }
//            is StrongEmphasis -> {
//                annotation.withStyle(boldStyle) {
//                    textParser(node = child, annotation = annotation)
//                }
//            }
//            is HardLineBreak -> annotation.append("\n")
//            is Link -> {
//                annotation.pushStyle(linkStyle)
//                annotation.pushStringAnnotation("URL", child.destination)
//                textParser(node = child, annotation = annotation)
//                annotation.pop()
//                annotation.pop()
//            }
//            is Text -> {
//                annotation.append(child.literal)
//            }
//            else -> textParser(node = child, annotation = annotation)
//        }
//        child = child.next
//    }
//}
