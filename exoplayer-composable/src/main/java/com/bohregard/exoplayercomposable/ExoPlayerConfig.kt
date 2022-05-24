package com.bohregard.exoplayercomposable

import androidx.annotation.FloatRange
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.Player.RepeatMode

/**
 * Configuration class to configure a [BaseExoPlayerComposable]
 *
 * @param autoPlay Auto play the given video. May require buffering before playback starts
 * @param controllerTimeOutMs If the controller is set, how long before it disappears
 * @param keepScreenOn Sets flags on the current window to keep the display on
 * @param repeatMode See [RepeatMode] for more information
 * @param showController Show the controller for the given [ExoPlayer] object
 * @param volume Set the volume of the player. Float value between 0 and 1 with 1 being the loudest
 *               and 0 being off.
 */
data class ExoPlayerConfig(
    val autoPlay: Boolean,
    val controllerTimeOutMs: Int,
    val keepScreenOn: Boolean,
    @RepeatMode
    val repeatMode: Int,
    val showController: Boolean,
    @FloatRange(from = 0.0, to = 1.0)
    val volume: Float,
    val zoomable: Boolean,
) {
    companion object {
        val DEFAULT = ExoPlayerConfig(
            autoPlay = false,
            controllerTimeOutMs = 0,
            keepScreenOn = false,
            repeatMode = REPEAT_MODE_OFF,
            showController = true,
            volume = 1f,
            zoomable = false
        )
    }
}