package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.Intent
import com.lm.hideapps.sources.microphone.MicrophoneService
import com.lm.hideapps.core.SPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SnoreServiceControlModule {

    @Provides
    @Singleton
    fun providesSnoreServiceControl(
        application: Application, sharedPrefProvider: SPreferences,
    ): ((Boolean) -> Unit) -> Unit =
        {
            Intent(application, MicrophoneService::class.java)
                .also { intent ->
                    if (!sharedPrefProvider.isRunning()) {
                        application.startForegroundService(intent); it(sharedPrefProvider.run())
                    } else {
                        application.stopService(intent); it(sharedPrefProvider.stop())
                    }
                }
        }
}