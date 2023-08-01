package com.bohregard.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import com.bohregard.markdown.components.MdBlockChildren
import com.bohregard.markdown.extensions.spoiler.SpoilerExtension
import com.bohregard.markdown.extensions.superscript.SuperscriptExtension
import com.bohregard.markdown.model.MarkdownActions
import com.bohregard.markdown.model.MarkdownConfiguration
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import java.io.BufferedReader
import java.io.StringReader

@Composable
fun MarkdownText(
    modifier: Modifier = Modifier,
    markdown: String,
    configuration: MarkdownConfiguration = MarkdownConfiguration(),
    textStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onSurface
    ),
) {
    var showSpoilers by rememberSaveable { mutableStateOf(false) }
    val markdownActions by remember {
        mutableStateOf(
            MarkdownActions(
                onSpoilersToggled = {
                    showSpoilers = it
                }
            )
        )
    }

    CompositionLocalProvider(
        LocalMarkdownTextStyle provides textStyle,
        LocalMarkdownConfiguration provides configuration,
        LocalMarkdownActions provides markdownActions,
        LocalMarkdownSpoilers provides showSpoilers
    ) {
        val parser = Parser.builder()
            .extensions(
                mutableListOf(
                    TablesExtension.create(),
                    StrikethroughExtension.create(),
                    SuperscriptExtension.create(),
                    AutolinkExtension.create(),
                    SpoilerExtension.create()
                )
            )
            .build()

        val sb = StringBuilder()
        val reader = BufferedReader(StringReader(markdown))

        var line = reader.readLine()
        while (line != null) {
            // Process Spoilers
            line = line.replace(">!(.*?)!<".toRegex(), "%%$1%%")

            // Process Carets
            // superscript uses ^ and expects a ' ' for each superscript.
            // Inject some spaces to compensate
            val caretCount = line.count { it == '^' }
            if (caretCount > 0) {
                val indexOfLastCaret = line.lastIndexOf('^')
                if (indexOfLastCaret >= 0) {
                    val indexOfFirstSpace =
                        line.substring(line.lastIndexOf('^')).indexOfFirst { it == ' ' }
                    val start = line.substring(0, indexOfLastCaret + indexOfFirstSpace)
                    val end = line.substring(indexOfLastCaret + indexOfFirstSpace)
                    val final = start + " ".repeat(caretCount) + end
                    sb.append(final)
                }
            } else {
                sb.append(line)
            }
            sb.append("\n")
            line = reader.readLine()
        }

        val document = parser.parse(sb.toString())

        if (BuildConfig.DEBUG) {
            printDocument(document)
        }

        Column(modifier = modifier) {
            // This is to track the state of showing spoilers. Otherwise the composition will never
            // recompose
//            LaunchedEffect(key1 = showSpoilers, block = {})
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