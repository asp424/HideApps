package com.lm.hideapps.sources.microphone

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.core.AppComponentGetter.appComponent
import com.lm.hideapps.notification.NotificationProvider
import javax.inject.Inject

class MicrophoneService : Service() {

    @Inject
    lateinit var micServiceHandler: MicServiceHandler

    @Inject
    lateinit var notificationProvider: NotificationProvider

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent.apply {
            inject(this@MicrophoneService)
            startForeground(101, notificationProvider.serviceNotification())
            micServiceHandler.micServiceJob((sharedPreferences().readLevel() * 20000).toInt())
            return START_NOT_STICKY
        }
    }

    override fun onBind(intent: Intent?) = LocalBinder().apply { micServiceHandler.bind = true }

    override fun onUnbind(intent: Intent?): Boolean {
        micServiceHandler.bind = false
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        micServiceHandler.bind = true
    }

    inner class LocalBinder : Binder() {
        fun service(): MicrophoneService = this@MicrophoneService
    }

    override fun onDestroy() {
        super.onDestroy()
        micServiceHandler.cancelJob()
    }
}