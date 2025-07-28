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
    WebImage(4000, 3000, "https://picsum.photos/1200/1600"),
    WebImage(3000, 4000, "https://picsum.photos/4000/3000"),
    WebImage(4000, 3000, "https://picsum.photos/3000/4000"),
    WebImage(600, 800, "https://picsum.photos/800/600"),
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