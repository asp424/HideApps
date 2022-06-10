package com.lm.hideapps.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.lm.hideapps.core.appComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class SnoreService : Service() {

    private var bind = false

    private var job: Job = Job()

    private val levelFlow =
        MutableSharedFlow<Short>(0, 0, BufferOverflow.SUSPEND)

    val micLevelFlow get() = levelFlow.asSharedFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        appComponent.notificationProvider().apply {

            startForeground(101, serviceNotification())

            job.cancel(); job = CoroutineScope(Dispatchers.IO).launch {

            appComponent.microphone().getMicLevelWithDefault.collect {
                levelFlow.emit(it)
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
        fun service(): SnoreService = this@SnoreService
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}