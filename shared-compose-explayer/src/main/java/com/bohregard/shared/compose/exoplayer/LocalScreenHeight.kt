package com.bohregard.shared.compose.exoplayer

import androidx.compose.runtime.staticCompositionLocalOf

val LocalScreenHeight = staticCompositionLocalOf<Int> {
    error("Height must be defined")
}