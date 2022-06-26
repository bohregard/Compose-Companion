package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.Heading

@Composable
internal fun MDHeading(heading: Heading) {
    val style = when (heading.level) {
//        1 -> MaterialTheme.typography.displayLarge
//        2 -> MaterialTheme.typography.displayMedium
//        3 -> MaterialTheme.typography.displaySmall
        1 -> MaterialTheme.typography.headlineLarge
        2 -> MaterialTheme.typography.headlineMedium
        3 -> MaterialTheme.typography.headlineSmall
        4 -> MaterialTheme.typography.titleLarge
        5 -> MaterialTheme.typography.titleMedium
        6 -> MaterialTheme.typography.titleSmall
        else -> MaterialTheme.typography.bodyLarge
    }

    Box {
        val text = buildAnnotatedString {
            AppendMarkdownChildren(heading)
        }
        Text(text, style = style)
    }
}