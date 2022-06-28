package com.bohregard.shared.compose.markdown.components

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