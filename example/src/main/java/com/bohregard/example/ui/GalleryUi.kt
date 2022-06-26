package com.bohregard.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bohregard.gallery.Gallery
import com.bohregard.gallery.model.BaseGalleryItem
import com.bohregard.gallery.model.WebImage

private val items = listOf<BaseGalleryItem>(
    WebImage(3000, 4000, "https://preview.redd.it/fqdk19afys791.jpg?width=4000&format=pjpg&auto=webp&s=822d2d2c7248b14959bf4ce9f219f779169ef9d8"),
    WebImage(4000, 3000, "https://preview.redd.it/reafmt1gys791.jpg?width=3000&format=pjpg&auto=webp&s=1c5fe37518be2d2954835027c13d364784200a0a"),
//    WebImage(600, 800, "https://picsum.photos/800/600"),
)

@Composable
fun GalleryUi() {

    Gallery(
        items = items,
    )
}

@Composable
fun GalleryCustomIndicatorUi() {
    Gallery(
        items = items,
        customIndicator = { page ->
            Text(
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(color = Color.Blue, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                text = "${page + 1}/${items.size}"
            )
        }
    )
}