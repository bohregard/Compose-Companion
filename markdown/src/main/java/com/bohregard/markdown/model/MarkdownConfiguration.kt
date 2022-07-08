package com.bohregard.markdown.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class MarkdownConfiguration(

    /**
     * onClickEvent - Called when the ClickableText is tapped, but the annotation has no available
     *                action to consume
     */
    val onClickEvent: () -> Unit = {},
) {
    var showSpoilers by mutableStateOf(false)
}