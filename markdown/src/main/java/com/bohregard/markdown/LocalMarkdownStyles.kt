package com.bohregard.markdown

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import com.bohregard.markdown.model.MarkdownActions
import com.bohregard.markdown.model.MarkdownConfiguration

val LocalMarkdownTextStyle = staticCompositionLocalOf {
    TextStyle()
}

val LocalMarkdownConfiguration = staticCompositionLocalOf {
    MarkdownConfiguration()
}

val LocalMarkdownSpoilers = staticCompositionLocalOf {
    false
}

val LocalMarkdownActions = staticCompositionLocalOf {
    MarkdownActions({})
}