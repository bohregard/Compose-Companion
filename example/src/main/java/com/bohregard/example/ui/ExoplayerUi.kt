package com.bohregard.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bohregard.example.R
import com.bohregard.exoplayercomposable.DataStoreCache
import com.bohregard.exoplayercomposable.ExoPlayerConfig
import com.bohregard.exoplayercomposable.ExoPlayerDashComposable
import com.bohregard.exoplayercomposable.LocalDataStoreCache

@Composable
fun ExoplayerUi(dataStoreCache: DataStoreCache) {
    CompositionLocalProvider(
        LocalDataStoreCache provides dataStoreCache
    ) {
        ExoPlayerDashComposable(
            config = ExoPlayerConfig.DEFAULT,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .fillMaxWidth(),
            dashUrl = "http://ftp.itec.aau.at/datasets/DASHDataset2014/BigBuckBunny/2sec/BigBuckBunny_2s_onDemand_2014_05_09.mpd",
            onError = {
                Text("Error Playing Video")
            },
            controls = { player, hasVolume, timeline, duration, isPlaying ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            if (player.isPlaying) {
                                player.pause()
                            } else {
                                player.play()
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = !isPlaying,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "Play"
                        )
                    }
                }
            }
        )
    }
}