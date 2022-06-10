package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.lm.hideapps.sources.microphone.MicrophoneService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Module
class BindSnoreServiceModule {

    @Provides
    @Singleton
    fun providesBindSnoreService(application: Application) = callbackFlow {
        var bound = false
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                trySendBlocking((service as MicrophoneService.LocalBinder).service()); bound =
                    true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                bound = false
            }
        }.apply {
            if (!bound) bound = application.bindService(
                Intent(application, MicrophoneService::class.java), this,
                Application.BIND_AUTO_CREATE
            )
            awaitClose {
                if (bound) application.unbindService(this); bound = false
                cancel()
            }
        }
    }
}