package com.bohregard.markdown.components

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import org.commonmark.node.Image

@Composable
internal fun MdImage(image: Image) {
    // Use coil?
    AsyncImage(
        model = image.destination,
        contentDescription = null
    )
}