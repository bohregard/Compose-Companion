package com.bohregard.markdown.model


class MarkdownConfiguration(

    /**
     * onClickEvent - Called when the ClickableText is tapped, but the annotation has no available
     *                action to consume
     */
    val onClickEvent: () -> Unit = {},
)