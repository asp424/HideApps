package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class PlaySoundModule {

    @Provides
    @Singleton
    fun providesPlaySound(application: Application): (Int, onRelease: () -> Unit) -> Unit =
        { source, onRelease ->
            CoroutineScope(IO).launch {
                runCatching { MediaPlayer.create(application, source) }
                    .onSuccess {
                        it.apply {
                            start(); setOnCompletionListener { release(); onRelease() }
                        }
                    }
            }
        }
}