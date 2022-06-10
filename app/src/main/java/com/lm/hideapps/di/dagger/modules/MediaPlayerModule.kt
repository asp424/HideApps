package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MediaPlayerModule {

    @Provides
    @Singleton
    fun providesMediaPlayer(application: Application): (Int) -> MediaPlayer = {
        MediaPlayer.create(application, it)
    }
}