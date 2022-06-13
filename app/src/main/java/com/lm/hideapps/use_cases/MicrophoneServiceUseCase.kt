package com.lm.hideapps.use_cases

import android.content.Context
import android.content.res.Resources
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.MediaPlayer
import com.lm.hideapps.R
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.core.StringToIntMapper
import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.data.remote_repositories.WeatherRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class MicrophoneServiceUseCase @Inject constructor(
    private val microphoneRepository: MicrophoneRepository,
    private val weatherRepository: WeatherRepository,
    private val mapper: StringToIntMapper,
    private val sPreferences: SPreferences,
    private val resources: Resources
) {

    private var bind = false
    var serviceJob: Job = Job()
    private var isTriggered = false

    val tempFlow =
        MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

    private suspend fun onTriggered(context: Context) {
        isTriggered = true
        emit("true")
        playSound(context, R.raw.triggered) {}
    }

    private suspend fun onGetTemperature(context: Context, temperature: String) {
        emit(temperature)
        playSound(
            context, mapper.stringToSoundId(
                temperature, resources.getString(R.string.raw)
            )
        ) { isTriggered = false }
    }

    private suspend fun emit(value: String) {
        if (bind) coroutineScope {
            launch(Dispatchers.IO) { tempFlow.emit(value) }
        }
    }

    fun onStartCommandWork(context: Context) {
        serviceJob.cancel()
        serviceJob = CoroutineScope(Dispatchers.IO).launch {
            microphoneRepository.microphoneLevelAsFlow(
                8000, CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT
            ).collect {
                if (it > triggeringLevel && !isTriggered) {
                    onTriggered(context)
                    weatherRepository.nowTemperature().collect { temperature ->
                        onGetTemperature(context, temperature)
                    }
                }
            }
        }
    }

    private fun playSound(context: Context, source: Int, onStop: () -> Unit = {}) =
        CoroutineScope(Dispatchers.IO).launch {
            var player: MediaPlayer? = null
            runCatching {
                player = MediaPlayer.create(context, source)
            }.onSuccess {
                player?.apply {
                    runCatching {
                        start()
                        setOnCompletionListener {
                            release()
                            player = null
                            onStop()
                        }
                    }
                }
            }
        }

    fun setBindTrue() {
        bind = true
    }

    fun setBindFalse() {
        bind = false
    }

    fun cancelServiceJob() {
        serviceJob.cancel()
    }

    private val triggeringLevel get() = sPreferences.readLevel() * 50000
}