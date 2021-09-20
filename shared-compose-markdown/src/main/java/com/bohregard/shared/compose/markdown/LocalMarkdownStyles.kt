package com.bohregard.shared.compose.markdown

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

val LocalMarkdownTextStyle = staticCompositionLocalOf {
    SpanStyle()
}

val LocalMarkdownBoldStyle = staticCompositionLocalOf {
    SpanStyle(
        fontWeight = FontWeight.Bold
    )
}