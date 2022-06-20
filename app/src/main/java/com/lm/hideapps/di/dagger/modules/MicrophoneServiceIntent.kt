package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.Intent
import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.service.LocalService
import com.lm.hideapps.service.Services
import dagger.Module
import dagger.Provides

@Module
class MicrophoneServiceIntentModule {

    @Provides
    @AppScope
    fun providesMicrophoneServiceIntent(application: Application) =
        Intent(application, LocalService::class.java).apply {
            action = Services.MICROPHONE.toString()
        }
}