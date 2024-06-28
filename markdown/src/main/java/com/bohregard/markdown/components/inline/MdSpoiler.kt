package com.bohregard.markdown.components.inline

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import com.bohregard.markdown.LocalMarkdownConfiguration
import com.bohregard.markdown.LocalMarkdownSpoilers
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.extensions.spoiler.Spoiler
import com.bohregard.markdown.util.TextParser

@Composable
internal fun MdSpoiler(annotatedStringBuilder: AnnotatedString.Builder, spoiler: Spoiler) {
    val showSpoilers = LocalMarkdownSpoilers.current
    val textStyle = LocalMarkdownTextStyle.current
    val spoilerStyle = textStyle.copy(
        background = if(showSpoilers) Color.Transparent else textStyle.color,
    ).toSpanStyle()

    annotatedStringBuilder.pushStyle(spoilerStyle)
    annotatedStringBuilder.pushStringAnnotation("SPOILER", "")
    TextParser(node = spoiler, annotatedStringBuilder = annotatedStringBuilder)
    annotatedStringBuilder.pop()
    annotatedStringBuilder.pop()
}