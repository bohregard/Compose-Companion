package com.bohregard.shared.compose.markdown

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.SpanStyle
import com.bohregard.shared.compose.markdown.components.MDBlockChildren
import com.bohregard.shared.compose.markdown.model.MarkdownConfiguration
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Document
import org.commonmark.parser.Parser

@Composable
fun MarkdownText(
    markdown: String,
    configuration: MarkdownConfiguration = MarkdownConfiguration( ),
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
        MDDocument(document = document as Document)
    }
}

@Composable
internal fun MDDocument(document: Document) {
    // TODO Wrap in a column???
    MDBlockChildren(document)
}