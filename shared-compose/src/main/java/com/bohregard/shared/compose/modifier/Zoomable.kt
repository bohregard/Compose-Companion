package com.bohregard.shared.compose.modifier

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onPlaced
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A modifier to enable zooming, panning, and rotation on an item. It has a default zoom min of 1f
 * and a max of 6f.
 *
 * @param enableRotation defaults to false, set true if you want to rotate the composable
 */
@Stable
fun Modifier.zoomable(enableRotation: Boolean = false): Modifier = composed {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange.times(scale)
    }

    zoomable(
        enableRotation,
        scale,
        rotation,
        offset,
        state,
        onZoomChange = { scaleChange, rotationChange, offsetChange ->
            scale = scaleChange
            rotation = rotationChange
            offset = offsetChange
        }
    )
}

/**
 * A modifier to enable zooming, panning, and rotation on an item. It has a default zoom min of 1f
 * and a max of 6f. This enables you to keep track of the scale, rotation, and offset yourself if
 * you need detailed and fine tuned control.x
 *
 * @param enableRotation defaults to false, set true if you want to rotate the composable
 */
@Stable
fun Modifier.zoomable(
    enableRotation: Boolean = false,
    scale: Float,
    rotation: Float,
    offset: Offset,
    state: TransformableState,
    onZoomChange: (scaleChange: Float, rotationChange: Float, offsetChange: Offset) -> Unit
) = composed {
    println("Offset: $offset")
    println("Rotation: $rotation")
    var layout: LayoutCoordinates? = null

    LaunchedEffect(state.isTransformInProgress) {
        if (!state.isTransformInProgress) {
            if (scale < 1f) {
                AnimationState(initialValue = 0f).animateTo(
                    1f,
                    SpringSpec(stiffness = Spring.StiffnessLow)
                ) {
                    val newScale = scale + (1 - scale) * this.value
                    val newOffset = offset * (1 - this.value)
                    onZoomChange(newScale, rotation, newOffset)
                }
            } else if (scale > 6f) {
                AnimationState(initialValue = 0f).animateTo(
                    1f,
                    SpringSpec(stiffness = Spring.StiffnessLow)
                ) {
                    val newScale = scale + (6 - scale) * this.value
                    val newOffset = offset * (1 - this.value)
                    onZoomChange(newScale, rotation, newOffset)
                }
            } else {
                if (layout == null) return@LaunchedEffect
                val maxX = layout!!.size.width * (scale - 1) / 2f
                val maxY = layout!!.size.height * (scale - 1) / 2f
                val target = Offset(
                    offset.x.coerceIn(-maxX, maxX),
                    offset.y.coerceIn(-maxY, maxY)
                )
                AnimationState(
                    typeConverter = Offset.VectorConverter,
                    initialValue = offset
                ).animateTo(target, SpringSpec(stiffness = Spring.StiffnessLow)) {
                    val newOffset = this.value
                    onZoomChange(scale, rotation, newOffset)
                }
            }
        }
    }

    val rads = rotation * PI.toFloat() / 180f
    println("Rads: $rads")
    this.then(
        onPlaced { layout = it }
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        drag(down.id) {
                            if (layout == null) return@drag
                            val maxX = layout!!.size.width * (scale - 1) / 2f
                            val maxY = layout!!.size.height * (scale - 1) / 2f
                            val targetTranslation = (it.positionChange() + offset)
                            if (targetTranslation.x > -maxX && targetTranslation.x < maxX &&
                                targetTranslation.y > -maxY && targetTranslation.y < maxY
                            ) {
                                onZoomChange(scale, rotation, targetTranslation)
                                it.consume()
                            }
                        }
                    }
                }
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = if(enableRotation) rotation else 0f,
                translationX = offset.x * cos(rads) - offset.y * sin(rads),
                translationY = offset.x * sin(rads) + offset.y * cos(rads),
            )
            .transformable(state = state)
    )
}