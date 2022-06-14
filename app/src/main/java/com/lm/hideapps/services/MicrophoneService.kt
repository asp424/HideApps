package com.lm.hideapps.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.core.appComponent
import kotlinx.coroutines.flow.asSharedFlow

class MicrophoneService : Service() {

    private val microphoneServiceUseCase by lazy { appComponent.microphoneServiceUseCase() }

    val temperatureForUI get() = microphoneServiceUseCase.temperatureForUIAsFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        microphoneServiceUseCase.onStartCommand(this)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = LocalBinder()
        .apply { microphoneServiceUseCase.onBind() }

    override fun onUnbind(intent: Intent?): Boolean {
        microphoneServiceUseCase.onUnbind()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        microphoneServiceUseCase.onBind()
    }

    inner class LocalBinder : Binder() {
        fun service(): MicrophoneService = this@MicrophoneService
    }

    override fun onDestroy() {
        super.onDestroy()
        microphoneServiceUseCase.cancelServiceJob()
    }
}