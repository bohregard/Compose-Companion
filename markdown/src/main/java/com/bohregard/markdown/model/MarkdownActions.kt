package com.bohregard.markdown.model

data class MarkdownActions(
    val onSpoilersToggled: (Boolean) -> Unit
)