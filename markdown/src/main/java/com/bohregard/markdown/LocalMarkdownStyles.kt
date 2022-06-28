package com.bohregard.markdown

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import com.bohregard.markdown.model.MarkdownConfiguration

val LocalMarkdownTextStyle = staticCompositionLocalOf {
    SpanStyle()
}

val LocalMarkdownConfiguration = staticCompositionLocalOf {
    MarkdownConfiguration()
}