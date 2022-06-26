package com.bohregard.gallery.model

import androidx.annotation.DrawableRes


abstract class BaseGalleryItem {
    abstract val height: Int
    abstract val width: Int
}

data class WebImage(
    override val height: Int,
    override val width: Int,
    val url: String
): BaseGalleryItem()

data class ResourceImage(
    override val height: Int,
    override val width: Int,
    @DrawableRes val resource: Int
): BaseGalleryItem()

data class Mp4Video(
    override val height: Int,
    override val width: Int,
    val url: String
): BaseGalleryItem()