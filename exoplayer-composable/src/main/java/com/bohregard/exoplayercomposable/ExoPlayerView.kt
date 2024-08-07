package com.bohregard.exoplayercomposable

import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bohregard.exoplayercomposable.extension.hasAudioTrack
import com.bohregard.shared.compose.modifier.zoomable
import com.bohregard.shared.extension.findActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * ExoPlayerDashComposable: given a dashUrl, will build and load a Surface view with the given dash
 * playlist.
 *
 * @param modifier The modifier that controls the bounding area of the BaseComposable
 * @param dashUrl The url to the dash playlist (ending in mpd generally)
 * @param config An [ExoPlayerConfig] object to control various properties of the player
 * @param cache A [DataStoreCache] object for cache control
 * @param onError A callback function for errors to display a custom UI. If no UI is provided, a default UI will be provided.
 * @param controls A callback function for controls with references to the player, volume status, timeline, duration, and playing status.
 * @return ExoPlayer
 */
@Composable
fun ExoPlayerDashComposable(
    modifier: Modifier = Modifier,
    dashUrl: String,
    config: ExoPlayerConfig,
    cache: DataStoreCache = LocalDataStoreCache.current,
    onError: (@Composable () -> Unit)? = null,
    controls: (@Composable BoxScope.(
        player: ExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
) {
    val context = LocalContext.current
    val player by remember(dashUrl) { mutableStateOf(ExoPlayer.Builder(context).build()) }

    val mediaSource: MediaSource by remember(dashUrl) {
        mutableStateOf(DashMediaSource.Factory(cache).createMediaSource(MediaItem.fromUri(dashUrl)))
    }

    LaunchedEffect(key1 = dashUrl, block = {
        player.setMediaSource(mediaSource)
        player.prepare()
    })

    BaseExoPlayerComposable(
        modifier = modifier,
        player = player,
        config = config,
        onError = onError,
        controls = controls
    )

    DisposableEffect(key1 = dashUrl) {
        onDispose {
            player.release()
        }
    }
}

/**
 * ExoPlayerCompose: given a MP4 url, will build and load a Surface view
 *
 * @param modifier The modifier that controls the bounding area of the BaseComposable
 * @param mp4Url the url to play
 * @param config An [ExoPlayerConfig] object to control various properties of the player
 * @param cache A [DataStoreCache] object for cache control
 * @param onError A callback function for errors to display a custom UI. If no UI is provided, a default UI will be provided.
 * @param controls A callback function for controls with references to the player, volume status, timeline, duration, and playing status.
 * @return ExoPlayer
 */
@Composable
fun ExoPlayerMp4Composable(
    modifier: Modifier = Modifier,
    mp4Url: String,
    config: ExoPlayerConfig,
    cache: DataStoreCache = LocalDataStoreCache.current,
    onError: (@Composable () -> Unit)? = null,
    controls: (@Composable BoxScope.(
        player: ExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
) {
    println("ANT_TEST: MP4URL - $mp4Url")
    val context = LocalContext.current
    val player by remember(mp4Url) { mutableStateOf(ExoPlayer.Builder(context).build()) }

    val mediaSource by remember(mp4Url) {
        mutableStateOf(
            ProgressiveMediaSource.Factory(cache).createMediaSource(MediaItem.fromUri(mp4Url))
        )
    }

    LaunchedEffect(key1 = mp4Url, block = {
        player.setMediaSource(mediaSource)
        player.prepare()
    })

    BaseExoPlayerComposable(
        modifier = modifier,
        player = player,
        config = config,
        onError = onError,
        controls = controls
    )

    DisposableEffect(key1 = mp4Url) {
        onDispose {
            player.release()
        }
    }
}

/**
 * Default wiring for the ExoPlayer view. Has the ability to keep the screen on if configured in the
 * [ExoPlayerConfig] object.
 *
 * @see ExoPlayerConfig
 * @see ExoPlayer
 * @param modifier The modifier that controls the bounding area of the BaseComposable
 * @param player The [ExoPlayer] object to play
 * @param config An [ExoPlayerConfig] object to control various properties of the player
 * @param onError A callback function for errors to display a custom UI. If no UI is provided, a default UI will be provided.
 * @param controls A callback function for controls with references to the player, volume status, timeline, duration, and playing status.
 */
@Composable
fun BaseExoPlayerComposable(
    modifier: Modifier = Modifier,
    player: ExoPlayer,
    config: ExoPlayerConfig,
    onError: (@Composable () -> Unit)?,
    controls: (@Composable BoxScope.(
        player: ExoPlayer,
        hasVolume: Boolean,
        timeline: Long,
        duration: Long,
        isPlaying: Boolean
    ) -> Unit)? = null,
) {
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    if (!isError) {
        Box(
            modifier = modifier
                .background(color = Color.Black)
                .clipToBounds()
        ) {
            var timeline by remember { mutableStateOf(0L) }
            var duration by remember { mutableStateOf(0L) }
            var isPlaying by remember { mutableStateOf(false) }
            var hasVolume by remember { mutableStateOf(false) }

            DisposableEffect("timelineTracker") {
                scope.launch {
                    while (true) {
                        timeline = player.currentPosition
                        delay(200)
                    }
                }
                onDispose {
                    scope.cancel()
                }
            }

            if (config.keepScreenOn) {
                val context = LocalContext.current

                DisposableEffect("keepScreenOn") {
                    val window = context.findActivity()?.window
                    window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    onDispose {
                        window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        scope.cancel()
                    }
                }
            }

            val zoomable = if (config.zoomable) {
                Modifier.zoomable()
            } else {
                Modifier
            }

            AndroidView(
                factory = {
                    val playerView = StyledPlayerView(it)
                    playerView.player = player
                    playerView.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    player.repeatMode = config.repeatMode
                    player.addListener(object : Player.Listener {

                        override fun onTracksChanged(tracks: Tracks) {
                            super.onTracksChanged(tracks)
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

                    player.volume = config.volume
                    player.playWhenReady = config.autoPlay
                    playerView.controllerHideOnTouch = config.showController
                    playerView.controllerAutoShow = config.showController
                    playerView.controllerShowTimeoutMs = config.controllerTimeOutMs
                    playerView.showController()
                    playerView
                },
                modifier = Modifier
                    .then(zoomable)
            )
            if (controls != null) {
                controls(
                    player = player,
                    hasVolume = hasVolume,
                    timeline = timeline,
                    duration = duration,
                    isPlaying = isPlaying
                )
            }
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