package com.bohregard.shared.compose.markdown.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import com.bohregard.shared.compose.markdown.util.TextParser
import org.commonmark.node.Heading

@Composable
internal fun MdHeading(heading: Heading) {
    val style = when (heading.level) {
        1 -> MaterialTheme.typography.headlineLarge
        2 -> MaterialTheme.typography.headlineMedium
        3 -> MaterialTheme.typography.headlineSmall
        4 -> MaterialTheme.typography.titleLarge
        5 -> MaterialTheme.typography.titleMedium
        6 -> MaterialTheme.typography.titleSmall
        else -> MaterialTheme.typography.bodyLarge
    }

    val annotatedStringBuilder = AnnotatedString.Builder()
    annotatedStringBuilder.withStyle(style.toSpanStyle()) {
        TextParser(node = heading, annotatedStringBuilder = annotatedStringBuilder)
    }
    MdClickableText(text = annotatedStringBuilder.toAnnotatedString())
}