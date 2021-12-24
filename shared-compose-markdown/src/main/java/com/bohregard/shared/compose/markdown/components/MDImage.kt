package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.commonmark.node.Image

@Composable
fun MDImage(image: Image) {
    println("Image: ${image.destination}")
    // Use coil?
    androidx.compose.foundation.Image(
        painter = rememberImagePainter("https://giphy.com/gifs/fXnRObM8Q0RkOmR5nf"),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}