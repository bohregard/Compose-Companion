package com.bohregard.shared.compose.exoplayer

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource


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
    clickable: SimpleExoPlayer.() -> Unit,
    ratio: Float
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }

    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
    val mediaSource: MediaSource = DashMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(dashUrl))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(player, clickable, ratio)

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
    clickable: SimpleExoPlayer.() -> Unit,
    ratio: Float
): SimpleExoPlayer {
    val tmp = SimpleExoPlayer.Builder(LocalContext.current).build()
    val player by remember { mutableStateOf(tmp) }


    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
    val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(mp4Url))
    player.setMediaSource(mediaSource)
    player.prepare()

    BaseExoPlayer(player, clickable, ratio)

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
    ratio: Float
) {
    AndroidView(
        viewBlock = ::StyledPlayerView,
        modifier = Modifier
            .clickable {
                clickable(player)
            }
            .fillMaxHeight()
            .fillMaxWidth()
            .aspectRatio(ratio)
    ) {
        it.player = player
        it.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        player.repeatMode = REPEAT_MODE_ALL
    }
}