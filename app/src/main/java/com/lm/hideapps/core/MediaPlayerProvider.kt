package com.lm.hideapps.core

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject

class MediaPlayerProvider @Inject constructor() {

    fun playSound(context: Context, source: Int, onStop: () -> Unit = {}) {
        var player: MediaPlayer? = null
        runCatching {
            player = MediaPlayer.create(context, source)
        }.onSuccess {
            player?.apply {
                setOnCompletionListener {
                    release()
                    player = null
                    onStop()
                }
                runCatching { start() }
            }
        }
    }
}