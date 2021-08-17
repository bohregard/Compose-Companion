package com.bohregard.shared.compose.exoplayer

import android.util.Log
import android.view.WindowMetrics
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.time.Instant
import java.time.LocalDateTime


private val TAG = "WaveCard"

/**
 * ExoPlayerCompose: given a dashUrl and a ratio, will build and load a Surface view with the
 * given dash playlist.
 *
 * @param dashUrl the url to play
 * @param ratio the ratio bound for the surface view
 */
@Composable
fun ExoPlayerDashCompose(
    dashUrl: String,
    ratio: Float,
    clickable: SimpleExoPlayer.() -> Unit,
    onError: (@Composable () -> Unit)? = null,
    autoPlay: Boolean = false,
    cache: DataStoreCache = LocalDataStoreCache.current
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }

    val mediaSource: MediaSource = DashMediaSource.Factory(cache)
        .createMediaSource(MediaItem.fromUri(dashUrl))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(player, clickable, onError, ratio, autoPlay)

    DisposableEffect(key1 = dashUrl) {
        onDispose {
            Log.d(TAG, "Player Release")
            player.release()
        }
    }
    return player
}

/**
 * ExoPlayerCompose: given a dashUrl and a ratio, will build and load a Surface view with the
 * given dash playlist.
 *
 * @param dashUrl the url to play
 * @param ratio the ratio bound for the surface view
 */
@Composable
fun ExoPlayerMp4Compose(
    mp4Url: String,
    ratio: Float,
    clickable: SimpleExoPlayer.() -> Unit,
    onError: (@Composable () -> Unit)? = null,
    autoPlay: Boolean = false,
    cache: DataStoreCache = LocalDataStoreCache.current
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }

    val mediaSource = ProgressiveMediaSource.Factory(cache)
        .createMediaSource(MediaItem.fromUri(mp4Url))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(player, clickable, onError, ratio, autoPlay)

    DisposableEffect(key1 = mp4Url) {
        onDispose {
            Log.d(TAG, "Player Release")
            player.release()
        }
    }
    return player
}

@Composable
fun BaseExoPlayer(
    player: SimpleExoPlayer,
    clickable: SimpleExoPlayer.() -> Unit,
    onError: (@Composable () -> Unit)?,
    ratio: Float,
    autoPlay: Boolean,
) {
    Log.d(TAG, "Base Exo Player")
    val height = LocalScreenHeight.current
    var isError by remember { mutableStateOf(false) }
    val layoutTime by remember { mutableStateOf(Instant.now().epochSecond) }
    if (!isError) {
        AndroidView(
            factory = ::StyledPlayerView,
            modifier = Modifier
                .clickable {
                    clickable(player)
                }
                .onGloballyPositioned {
                    val now = Instant.now().epochSecond
                    if (now - layoutTime < 2) return@onGloballyPositioned
                    val top = it.boundsInRoot().top
                    val bottom = it.boundsInRoot().bottom
                    val middleTop = it.boundsInRoot().height / 2 + top
                    val middleBottom = bottom - it.boundsInRoot().height / 2

                    val topBound = height * 1 / 3
                    val bottomBound = height * 2 / 3

                    if (middleTop > topBound && middleBottom < bottomBound) {
                        if (!player.isPlaying && autoPlay) {
                            Log.d(TAG, "Start playing")
                            player.playWhenReady = true
                        }
                    } else {
                        if (player.isPlaying) {
                            Log.d(TAG, "Stop playing")
                            player.pause()
                        }
                    }
                }
                .fillMaxHeight()
                .fillMaxWidth()
                .aspectRatio(ratio)
        ) {
            it.player = player
            it.hideController()
            it.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            player.repeatMode = REPEAT_MODE_ALL
            player.addListener(object : Player.EventListener {
                override fun onPlayerError(error: ExoPlaybackException) {
                    super.onPlayerError(error)
                    error.printStackTrace()
                    isError = true
                }
            })

            player.volume = 0f
            player.playWhenReady = false
        }
    } else {
        onError?.invoke()
    }
}