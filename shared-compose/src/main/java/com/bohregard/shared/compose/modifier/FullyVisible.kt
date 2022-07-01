package com.bohregard.shared.compose.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.fullyVisible(
    lowerBound: Float = 0f,
    upperBound: Float = 0f,
    callback: (visible: Boolean) -> Unit
) = composed {
    val config = LocalConfiguration.current
    val density = LocalDensity.current
    val height = with(density) { config.screenHeightDp.dp.toPx() }
    val upperBoundActual = if (upperBound == 0f) height else upperBound
    var isVisible: Boolean

    this.then(
        onPlaced {
            val windowPosition = it.positionInRoot()
            val center = it.size.height / 2 + windowPosition.y
            val top = center - it.size.height / 2
            val bottom = center + it.size.height / 2
            isVisible = top >= lowerBound && bottom <= upperBoundActual

            callback(isVisible)
        }
    )
}