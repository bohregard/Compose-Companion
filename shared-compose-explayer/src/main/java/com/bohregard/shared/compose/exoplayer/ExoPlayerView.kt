package com.bohregard.shared.compose.exoplayer

import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bohregard.shared.compose.exoplayer.extension.hasAudioTrack
import com.bohregard.shared.extension.findActivity
import com.bohregard.shared.extension.toHourMinuteSecondString
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter


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
    modifier: Modifier = Modifier,
    dashUrl: String,
    onError: (@Composable () -> Unit)? = null,
    autoPlay: Boolean = false,
    cache: DataStoreCache = LocalDataStoreCache.current,
    controls: (@Composable (
        player: SimpleExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }

    val mediaSource: MediaSource = DashMediaSource.Factory(cache)
        .createMediaSource(MediaItem.fromUri(dashUrl))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(
        modifier = modifier,
        player = player,
        onError = onError,
        autoPlay = autoPlay,
        controls = controls
    )

    Log.d(TAG, "Player Key: $dashUrl")

    DisposableEffect(key1 = dashUrl) {
        onDispose {
            Log.d(TAG, "Player Release $dashUrl")
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
    modifier: Modifier = Modifier,
    mp4Url: String,
    onError: (@Composable () -> Unit)? = null,
    autoPlay: Boolean = false,
    cache: DataStoreCache = LocalDataStoreCache.current,
    controls: (@Composable (
        player: SimpleExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }

    val mediaSource = ProgressiveMediaSource.Factory(cache)
        .createMediaSource(MediaItem.fromUri(mp4Url))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(
        modifier = modifier,
        player = player,
        onError = onError,
        autoPlay = autoPlay,
        controls = controls
    )

    DisposableEffect(key1 = mp4Url) {
        onDispose {
            Log.d(TAG, "Player Release: $mp4Url")
            player.release()
        }
    }
    return player
}

@Composable
fun BaseExoPlayer(
    modifier: Modifier = Modifier,
    player: SimpleExoPlayer,
    onError: (@Composable () -> Unit)?,
    autoPlay: Boolean,
    controls: (@Composable (
        player: SimpleExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
) {
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    if (!isError) {
        Column(
            modifier = modifier.background(color = Color.Black)
        ) {
            var timeline by remember { mutableStateOf(0L) }
            var duration by remember { mutableStateOf(0L) }
            var isPlaying by remember { mutableStateOf(false) }
            var hasVolume by remember { mutableStateOf(false) }

            val context = LocalContext.current
            DisposableEffect(Unit) {
                val window = context.findActivity()?.window
                window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                scope.launch {
                    while (true) {
                        timeline = player.currentPosition
                        delay(200)
                    }
                }
                onDispose {
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    scope.cancel()
                }
            }
            AndroidView(
                factory = {
                    val playerView = StyledPlayerView(it)
                    playerView.player = player
                    playerView.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    player.repeatMode = REPEAT_MODE_ALL
                    player.addListener(object : Player.Listener {

                        override fun onTracksChanged(
                            trackGroups: TrackGroupArray,
                            trackSelections: TrackSelectionArray
                        ) {
                            super.onTracksChanged(trackGroups, trackSelections)
                            hasVolume = trackGroups.hasAudioTrack()
                        }

                        override fun onPlaybackStateChanged(state: Int) {
                            super.onPlaybackStateChanged(state)
                            isPlaying = player.playWhenReady && state == STATE_READY
                            duration = player.duration
                        }

                        override fun onIsPlayingChanged(d: Boolean) {
                            super.onIsPlayingChanged(d)
                            duration = player.duration
                            isPlaying = d
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            super.onPlayerError(error)
                            error.printStackTrace()
                            isError = true
                        }
                    })

                    player.volume = 0f
                    player.playWhenReady = autoPlay
                    playerView.controllerHideOnTouch = false
                    playerView.controllerAutoShow = false
                    playerView.controllerShowTimeoutMs = 0
                    playerView.showController()
                    playerView
                },
                modifier = Modifier
                    .weight(1f)
            )
            controls?.invoke(
                player,
                hasVolume,
                timeline,
                duration,
                isPlaying
            )
        }
    } else {
        onError?.invoke()
    }

    DisposableEffect(key1 = null) {
        onDispose {
            scope.cancel()
        }
    }
}