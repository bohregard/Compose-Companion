package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.Heading

@Composable
fun MDHeading(heading: Heading) {
    val style = when (heading.level) {
        1 -> MaterialTheme.typography.h1
        2 -> MaterialTheme.typography.h2
        else -> MaterialTheme.typography.h3
    }

    Box {
        val text = buildAnnotatedString {
            AppendMarkdownChildren(heading)
        }
        Text(text, style = style)
    }
}