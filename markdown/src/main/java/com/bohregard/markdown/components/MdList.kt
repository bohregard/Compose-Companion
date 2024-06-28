package com.bohregard.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.util.TextParser
import org.commonmark.node.BulletList
import org.commonmark.node.Node
import org.commonmark.node.OrderedList

@Composable
internal fun MdList(list: Node) {
    val style = LocalMarkdownTextStyle.current
    val annotatedStringBuilder = AnnotatedString.Builder()
    var child = list.firstChild
    var count = if (list is OrderedList) list.startNumber - 1 else 0
    while (child != null) {
        annotatedStringBuilder.pushStyle(style.toSpanStyle())
        when (list) {
            is BulletList -> {
                annotatedStringBuilder.append("• ")
            }
            is OrderedList -> {
                count++
                annotatedStringBuilder.append("$count${list.delimiter} ")
            }
        }
        annotatedStringBuilder.pop()
        TextParser(
            node = child,
            annotatedStringBuilder = annotatedStringBuilder
        )
        annotatedStringBuilder.append("\n")

        child = child.next
    }
    MdClickableText(annotatedStringBuilder = annotatedStringBuilder)

}