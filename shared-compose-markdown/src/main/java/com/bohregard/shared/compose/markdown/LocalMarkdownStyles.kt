package com.bohregard.shared.compose.markdown

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import com.bohregard.shared.compose.markdown.model.MarkdownConfiguration

val LocalMarkdownTextStyle = staticCompositionLocalOf {
    SpanStyle()
}

val LocalMarkdownConfiguration = staticCompositionLocalOf {
    MarkdownConfiguration()
}