package com.bohregard.shared.compose.markdown.components

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import org.commonmark.node.Image

@Composable
internal fun MDImage(image: Image) {
    // Use coil?
    AsyncImage(
        model = "https://giphy.com/gifs/fXnRObM8Q0RkOmR5nf",
        contentDescription = null
    )
}