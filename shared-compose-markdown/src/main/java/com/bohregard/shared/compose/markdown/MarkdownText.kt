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

        if (BuildConfig.DEBUG) {
            printDocument(document)
        }
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