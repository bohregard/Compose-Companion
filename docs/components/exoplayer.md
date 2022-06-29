# ExoPlayer Composable

<img alt="Time Picker detail" src="/img/ExoPlayer.png" width="200"/>

A wrapper around ExoPlayer's UI view. Use either the `ExoPlayerDashComposable` or the `ExoPlayerMp4Composable`. Both return the `ExoPlayer` object for use as needed. If you need more control over the `ExoPlayer` object, you can use the `BaseExoPlayerComposable` and pass in your own player.

When the composable is disposed, the player is released. However if you use the `BaseExoPlayerComposable` you are responsible for releasing the player.

An XML layout can be used as needed. Follow the [instructions](https://exoplayer.dev/ui-components.html) on the official documentation. A composable can also be used if the `controls` field is passed in. This uses a `BoxScope` and is overlayed on the component. See the example below for more details on `controls`.

## Zoom/Pan

Zooming and Panning is supported if it's needed. To enabled, set `zoomable = true` on the `ExoPlayerConfig` object. The minimum zoom is 1f and the maximum zoom is 6f. The panning is bounded and clipped by the box it's contained in.

**Note**: If you have controls defined (via compose or XML), they will take precedent over the drag/pan gestures. However the controls can be placed below the ExoPlayer view as necessary.

## Example code:

```groovy
implementation 'com.bohregard:exoplayercomposable:<latest-version>'
```

```kotlin
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
```
