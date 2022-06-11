package com.lm.hideapps.sources.microphone

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.R
import com.lm.hideapps.core.AppComponentGetter.appComponent
import com.lm.hideapps.utils.getSound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class MicrophoneService : Service() {

    private var bind = false

    private var job: Job = Job()

    private val tempFlow = MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

    val temp get() = tempFlow.asSharedFlow()

    private var isLoadingTemp = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent.apply {
            startForeground(101, notificationProvider().serviceNotification())

            job.cancel(); if (!job.isActive) job = CoroutineScope(IO).launch {
            microphone().getMicLevelWithDefault.collect {
                if (it > 15000 && !isLoadingTemp) {
                    if (bind) tempFlow.emit("true")
                    isLoadingTemp = true
                    playSound().invoke(R.raw.a){}
                    jsoupRepository().gismeteoNowTemp().collect { temp ->
                        if (bind) tempFlow.emit(temp)
                        playSound().invoke(temp.getSound()){ isLoadingTemp = false }
                    }
                }
            }
        }
            return START_NOT_STICKY
        }
    }

    override fun onBind(intent: Intent?) = LocalBinder().apply { bind = true }

    override fun onUnbind(intent: Intent?): Boolean {
        bind = false
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        bind = true
    }

    inner class LocalBinder : Binder() {
        fun service(): MicrophoneService = this@MicrophoneService
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}