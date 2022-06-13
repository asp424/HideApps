package com.lm.hideapps.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.core.appComponent
import com.lm.hideapps.notifications.Notifications
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class MicrophoneService : Service() {

    @Inject
    lateinit var notifications: Notifications

    @Inject
    lateinit var microphoneServiceUseCase: MicrophoneServiceUseCase

    val tempForUI get() = microphoneServiceUseCase.tempFlow.asSharedFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent.inject(this@MicrophoneService)
        startForeground(101, notifications.showForegroundNotification())
        microphoneServiceUseCase.onStartCommandWork(this)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = LocalBinder()
        .apply { microphoneServiceUseCase.setBindTrue() }

    override fun onUnbind(intent: Intent?): Boolean {
        microphoneServiceUseCase.setBindFalse()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        microphoneServiceUseCase.setBindTrue()
    }

    inner class LocalBinder : Binder() {
        fun service(): MicrophoneService = this@MicrophoneService
    }

    override fun onDestroy() {
        super.onDestroy()
        microphoneServiceUseCase.cancelServiceJob()
    }
}