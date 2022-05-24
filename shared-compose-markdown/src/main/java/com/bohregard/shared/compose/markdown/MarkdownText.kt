package com.bohregard.shared.compose.markdown

import androidx.compose.runtime.Composable
import com.bohregard.shared.compose.markdown.components.MDBlockChildren
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Document
import org.commonmark.parser.Parser

@Composable
fun MarkdownText(markdown: String) {
    val parser = Parser.builder()
        .extensions(mutableListOf(TablesExtension.create()))
        .build()
    val document = parser.parse(markdown.replace("^(#+)([^\\s#].*)\$".toRegex(), "$1 $2"))
    MDDocument(document = document as Document)
}

@Composable
internal fun MDDocument(document: Document) {
    MDBlockChildren(document)
}