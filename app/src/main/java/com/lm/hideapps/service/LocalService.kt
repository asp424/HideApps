package com.lm.hideapps.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.core.appComponent
import com.lm.hideapps.core.log
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import javax.inject.Inject

class LocalService: Service() {

    @Inject
    lateinit var microphoneServiceUseCase: MicrophoneServiceUseCase

    @Inject
    lateinit var localServiceBinder: LocalServiceBinder

    val temperatureForUI get() = microphoneServiceUseCase.temperatureForUIAsFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == Services.MICROPHONE.toString()){
            appComponent.inject(this)
            microphoneServiceUseCase.onStartCommand(this)
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = LocalBinder().apply {
        localServiceBinder.bind()
    }

    inner class LocalBinder : Binder() {
        fun service(): LocalService = this@LocalService
    }

    override fun onRebind(intent: Intent?) {
        localServiceBinder.bind()
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        localServiceBinder.unBind()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        microphoneServiceUseCase.cancelServiceJob()
    }
}