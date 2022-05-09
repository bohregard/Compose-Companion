package com.bohregard.exoplayercomposable.extension

import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.util.MimeTypes

fun TrackGroupArray.hasAudioTrack(): Boolean {
    for (i in 0 until length) {
        for (j in 0 until get(i).length) {
            if (MimeTypes.isAudio(get(i).getFormat(j).sampleMimeType)) {
                return true
            }
        }
    }
    return false
}
