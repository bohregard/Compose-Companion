@file:Suppress("unused")

package com.bohregard.shared.compose.components

/*
 * Copyright 2020 The Android Open Source Project
 * Copyright 2021 Albert Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.ViewConfiguration
import androidx.compose.animation.core.*
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import kotlinx.coroutines.coroutineScope
import kotlin.math.*

/**
 * This is a modified version of:
 * https://github.com/android/compose-samples/blob/main/Jetcaster/app/src/main/java/com/example/jetcaster/util/Pager.kt
 * with more natural fling animation and supports of nested scrolling, state restoration, etc.
 */

@Composable
fun rememberPagerState(
    pageCount: Int,
    initialIndex: Int = 0,
    interactionSource: MutableInteractionSource? = null
): PagerState2 {
    val velocity = ViewConfiguration.get(LocalContext.current).scaledMinimumFlingVelocity
    val stateHolder = rememberSaveableStateHolder()

    val saver = remember(interactionSource, velocity, stateHolder) {
        PagerState2.Saver(interactionSource, velocity, stateHolder)
    }

    val state = rememberSaveable(interactionSource, velocity, stateHolder, saver = saver) {
        PagerState2(initialIndex, interactionSource, velocity, stateHolder)
    }
    state.pageCount = pageCount
    return state
}

@Stable
fun PagerState2.tabIndicator(): @Composable (tabPositions: List<TabPosition>) -> Unit =
    { tabPositions ->
        val tab = tabPositions[pageIndex]
        val width = tab.width
        val left = tab.left - width * pageOffsetFraction
        TabRowDefaults.Indicator(
            modifier = Modifier.fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset(x = left)
                .width(width)
        )
    }

class PagerState2 internal constructor(
    initialIndex: Int = 0,
    val interactionSource: MutableInteractionSource? = null,
    private val minimumFlingVelocity: Int,
    internal val stateHolder: SaveableStateHolder
) : ScrollableState, FlingBehavior {

    var pageCount: Int = 0
        set(value) {
            field = value
            if (pageIndexNonObservable >= pageCount) {
                pageIndexNonObservable = pageCount - 1
            }
            if (pageIndexNonObservable == pageCount - 1 && pageOffsetNonObservable < 0) {
                pageOffsetNonObservable = 0
            }
        }

    var pageIndexNonObservable: Int = initialIndex
        private set(value) {
            field = value
            pageIndex = value
        }

    var pageIndex: Int by mutableStateOf(pageIndexNonObservable)
        private set

    var pageOffsetNonObservable: Int = 0
        private set(value) {
            field = value
            pageOffset = value
        }

    var pageOffset: Int by mutableStateOf(0)
        private set

    var pageWidth: Int by mutableStateOf(0)
        internal set

    val pageOffsetFraction: Float
        get() = pageOffset.toFloat() / pageWidth

    private lateinit var remeasurement: Remeasurement

    internal val remeasurementModifier = object : RemeasurementModifier {
        override fun onRemeasurementAvailable(remeasurement: Remeasurement) {
            this@PagerState2.remeasurement = remeasurement
        }
    }

    private val scrollScope: ScrollScope = object : ScrollScope {
        override fun scrollBy(pixels: Float): Float = onScroll(pixels)
    }

    private val scrollMutex = MutatorMutex()

    private val isScrollingState = mutableStateOf(false)

    private var scrollToBeConsumed = 0f

    private val globalOffset: Int
        get() = pageIndexNonObservable * pageWidth - pageOffsetNonObservable

    private fun onScroll(distance: Float): Float {
        if (pageCount == 0 ||
            distance > 0 && pageIndexNonObservable == pageCount - 1 && pageOffsetNonObservable <= 0 ||
            distance < 0 && pageIndexNonObservable == 0 && pageOffsetNonObservable >= 0
        ) {
            return 0f
        }
        check(abs(scrollToBeConsumed) <= 0.5f) {
            "entered drag with non-zero pending scroll: $scrollToBeConsumed"
        }
        scrollToBeConsumed += distance

        // We do scrollToBeConsumed.roundToInt() so there will be no scroll if
        // we have less than 0.5 pixels
        if (abs(scrollToBeConsumed) > 0.5f) {
            val oldGlobalOffset = globalOffset
            val scrollDelta = scrollToBeConsumed.roundToInt()
            val newGlobalOffset = (oldGlobalOffset + scrollDelta)
                .coerceIn(0, (pageCount - 1) * pageWidth)
            val consumed = newGlobalOffset - oldGlobalOffset
            scrollToBeConsumed -= consumed
            pageIndexNonObservable = (newGlobalOffset.toFloat() / pageWidth).roundToInt()
            pageOffsetNonObservable = pageIndexNonObservable * pageWidth - newGlobalOffset
            remeasurement.forceRemeasure()
        }

        return if (abs(scrollToBeConsumed) <= 0.5f) {
            // We consumed all of it - we'll hold onto the fractional scroll for later, so report
            // that we consumed the whole thing
            distance
        } else {
            val scrollConsumed = distance - scrollToBeConsumed
            // We did not consume all of it - return the rest to be consumed elsewhere (e.g.,
            // nested scrolling)
            scrollToBeConsumed = 0f // We're not consuming the rest, give it back
            scrollConsumed
        }
    }

    override val isScrollInProgress: Boolean
        get() = isScrollingState.value

    override fun dispatchRawDelta(delta: Float): Float = onScroll(delta)

    override suspend fun scroll(
        scrollPriority: MutatePriority,
        block: suspend ScrollScope.() -> Unit
    ) = coroutineScope {
        scrollMutex.mutateWith(scrollScope, scrollPriority) {
            isScrollingState.value = true
            block()
            isScrollingState.value = false
        }
    }

    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        val velocity = if (abs(initialVelocity) >= minimumFlingVelocity) initialVelocity else 0f
        val scrollOffset = -calculateNearestPageOffset(velocity)
        val animSpec = spring<Float>().vectorize(Float.VectorConverter)
        val conv = Float.VectorConverter
        val zeroVector = conv.convertToVector(0f)
        val targetVector = conv.convertToVector(scrollOffset)
        val velocityVector = conv.convertToVector(initialVelocity)
        var previousValue = 0f
        val startTimeNanos = withFrameNanos { it }

        do {
            val finished = withFrameNanos { frameTimeNanos ->
                val newValue = conv.convertFromVector(
                    animSpec.getValueFromNanos(
                        playTimeNanos = frameTimeNanos - startTimeNanos,
                        initialValue = zeroVector,
                        targetValue = targetVector,
                        initialVelocity = velocityVector
                    )
                )
                val delta = newValue - previousValue
                scrollBy(delta)
                previousValue = newValue
                newValue == scrollOffset
            }
        } while (!finished)

        return 0f
    }

    private fun calculateNearestPageOffset(velocity: Float): Float {
        val currentOffset = globalOffset.toFloat()
        val times = currentOffset / pageWidth
        var nearestIndex = when {
            velocity > 0 -> floor(times)
            velocity < 0 -> ceil(times)
            else -> round(times)
        }
        if (nearestIndex == times) {
            // Ensure that page moves as long as there is a non-zero velocity
            nearestIndex -= sign(velocity)
        }
        val nearestOffset = nearestIndex * pageWidth
        return nearestOffset - currentOffset
    }

    fun snapTo(pageIndex: Int) {
        pageIndexNonObservable = pageIndex
        pageOffsetNonObservable = 0
    }

    suspend fun animateSnapTo(pageIndex: Int) {
        val scrollOffset = pageIndex * pageWidth - globalOffset
        animateScrollBy(scrollOffset.toFloat())
    }

    companion object {
        fun Saver(
            interactionSource: MutableInteractionSource?,
            minimumFlingVelocity: Int,
            stateHolder: SaveableStateHolder
        ): Saver<PagerState2, *> = listSaver(
            save = { listOf(it.pageCount, it.pageIndexNonObservable) },
            restore = {
                PagerState2(
                    initialIndex = it[1],
                    interactionSource = interactionSource,
                    minimumFlingVelocity = minimumFlingVelocity,
                    stateHolder = stateHolder
                ).apply { pageCount = it[0] }
            }
        )
    }

}

@Immutable
private data class PageData2(val pageIndex: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = this@PageData2
}

private val Measurable.pageIndex: Int
    get() = (parentData as? PageData2)?.pageIndex ?: error("No PageData for measurable $this")

@Composable
fun Pager2(
    state: PagerState2,
    modifier: Modifier = Modifier,
    offscreenLimit: Int = 1,
    reverseLayout: Boolean = false,
    pageContent: @Composable BoxScope.(Int) -> Unit
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    // reverse scroll by default, to have "natural" gesture that goes reversed to layout
    // if rtl and horizontal, do not reverse to make it right-to-left
    val reverseScrollDirection = if (isRtl) reverseLayout else !reverseLayout
    val contentFactory = wrapWithStateRestoration(state.stateHolder) { index ->
        { pageContent(index) }
    }

    Layout(
        content = {
            val minExtra = if (offscreenLimit == 0 && state.pageOffset > 0) -1 else 0
            val maxExtra = if (offscreenLimit == 0 && state.pageOffset < 0) 1 else 0
            val minPage = (state.pageIndex - offscreenLimit + minExtra)
                .coerceAtLeast(0)
            val maxPage = (state.pageIndex + offscreenLimit + maxExtra)
                .coerceAtMost(state.pageCount - 1)

            for (page in minPage..maxPage) {
                val pageData = PageData2(page)
                key(pageData) {
                    Box(contentAlignment = Alignment.Center, modifier = pageData) {
                        contentFactory(page).invoke()
                    }
                }
            }
        },
        modifier = modifier.scrollable(
            state = state,
            orientation = Orientation.Horizontal,
            reverseDirection = reverseScrollDirection,
            flingBehavior = state,
            interactionSource = state.interactionSource
        ).clipToBounds().then(state.remeasurementModifier)
    ) { measurables, constraints ->
        val pageWidth = constraints.maxWidth
        val pageHeight = constraints.maxHeight
        state.pageWidth = pageWidth
        layout(pageWidth, pageHeight) {
            val currentIndex = state.pageIndexNonObservable
            val currentOffset = state.pageOffsetNonObservable
            val childConstraints = Constraints.fixed(pageWidth, pageHeight)
            measurables.fastForEach {
                val placeable = it.measure(childConstraints)
                val offset = (it.pageIndex - currentIndex) * pageWidth + currentOffset
                placeable.place(offset, 0)
            }
        }
    }
}

/**
 * Converts page content factory to another one which adds auto state restoration functionality.
 */
@Composable
private fun wrapWithStateRestoration(
    stateHolder: SaveableStateHolder,
    pageContent: BoxScope.(Int) -> @Composable () -> Unit
): BoxScope.(Int) -> @Composable () -> Unit {
    return remember(stateHolder, pageContent) {
        { index ->
            val content = pageContent(index)
            // we just wrap our original lambda with the one which auto restores the state
            // currently we use index in the list as a key for the restoration, but in the future
            // we will use the user provided key
            (@Composable { stateHolder.SaveableStateProvider(index, content) })
        }
    }
}
