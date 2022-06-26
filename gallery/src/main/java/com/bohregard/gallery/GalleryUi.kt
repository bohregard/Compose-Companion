package com.bohregard.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bohregard.gallery.model.BaseGalleryItem
import com.bohregard.gallery.model.Mp4Video
import com.bohregard.gallery.model.ResourceImage
import com.bohregard.gallery.model.WebImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Gallery(
    modifier: Modifier = Modifier,
    items: List<BaseGalleryItem>,
    customIndicator: (@Composable BoxScope.(page: Int) -> Unit)? = null,
    onMp4VideoLayout: @Composable (Mp4Video) -> Unit = {}
) {
    val pagerState = rememberPagerState()

    val largestAspectRatio = items.minByOrNull { it.width / it.height.toFloat() }?.let {
        it.width / it.height.toFloat()
    } ?: 0f

    println("Smallest Aspect: $largestAspectRatio")

    Box(
        modifier = modifier.fillMaxWidth()
            .aspectRatio(largestAspectRatio)
    ) {
        HorizontalPager(
            count = items.size,
            state = pagerState,
        ) { page ->

            val item = items[page]

            println("Aspect Ratio: ${item.width / item.height.toFloat()}")
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when(item) {
                    is WebImage -> {
                        AsyncImage(
                            model = item.url,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .aspectRatio(item.width / item.height.toFloat()),
                            contentDescription = null
                        )
                    }
                    is ResourceImage -> {}
                    is Mp4Video -> onMp4VideoLayout(item)
                }

                customIndicator?.invoke(this, page)
            }
        }

        if (customIndicator == null) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewGallery() {
    val items = listOf<BaseGalleryItem>(
        WebImage(300, 200, "https://picsum.photos/200/300"),
        WebImage(300, 400, "https://picsum.photos/400/300"),
        WebImage(300, 600, "https://picsum.photos/600/300"),
    )
    Gallery(items = items)
}