package com.lm.hideapps.sources.microphone

import android.Manifest.permission.RECORD_AUDIO
import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioFormat.CHANNEL_IN_MONO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.MediaRecorder.AudioSource.MIC
import android.os.Binder
import com.lm.hideapps.R
import com.lm.hideapps.core.AppComponentGetter.appComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class MicrophoneService : Service() {

    private var bind = false

    private var job: Job = Job()

    private val levelFlow = MutableSharedFlow<Int>(0, 0, BufferOverflow.SUSPEND)

    val micLevelFlow get() = levelFlow.asSharedFlow()

    private val mediaPlayer by lazy { appComponent.mediaPlayer().invoke(R.raw.a) }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(101, appComponent.notificationProvider().serviceNotification())

        job.cancel(); if (!job.isActive) job = CoroutineScope(IO).launch {
            appComponent.microphone().getMicLevelWithDefault.collect {
                levelFlow.emit(it)
                if (it > 3000) mediaPlayer.start()
            }
        }
        return START_NOT_STICKY
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
        mediaPlayer.release()
    }

    private val minSize by lazy { AudioRecord.getMinBufferSize(config[0], config[1], config[2]) }
    private val buffer by lazy { ShortArray(minSize) }
    private val config by lazy {
        listOf(
            8000,
            AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT
        )
    }
}