package com.lm.hideapps.use_cases

import android.content.Context
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.lm.hideapps.R
import com.lm.hideapps.core.MediaPlayerProvider
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.data.remote_repositories.LoadWeatherStates
import com.lm.hideapps.data.remote_repositories.WeatherRepository
import com.lm.hideapps.notifications.Notifications
import com.lm.hideapps.presentation.ui.screens.TemperatureStates
import com.lm.hideapps.service.LocalService
import com.lm.hideapps.service.LocalServiceBindState
import com.lm.hideapps.service.LocalServiceBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MicrophoneServiceUseCase {

    fun onStartCommand(context: LocalService)

    fun cancelServiceJob()

    fun temperatureForUIAsFlow(): SharedFlow<TemperatureStates>

    class Base @Inject constructor(
        private val microphoneRepository: MicrophoneRepository,
        private val weatherRepository: WeatherRepository,
        private val notifications: Notifications,
        private val mediaPlayer: MediaPlayerProvider,
        private val sPreferences: SPreferences,
        private val localServiceBinder: LocalServiceBinder
    ) : MicrophoneServiceUseCase {

        override fun onStartCommand(context: LocalService) {
            context.startForeground(101, notifications.showForegroundNotification())
            startServiceJob {
                microphoneRepository.microphoneLevelAsFlow(
                    8000, CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT
                ).collect {
                    onGetLevel(it, context)
                }
            }
        }

        override fun cancelServiceJob() = serviceJob.value.cancel()

        override fun temperatureForUIAsFlow() = temperatureForUIAsFlow.asSharedFlow()

        private fun startServiceJob(
            onLaunch: suspend CoroutineScope.() -> Unit
        ) {
            cancelServiceJob()
            serviceJob.value = CoroutineScope(IO)
                .launch { onLaunch(this) }
        }

        private suspend fun onGetLevel(level: Int, context: Context) {
            if (level > triggeringLevel && !isDo.value) {
                isDo.value = START_LOADING
                TemperatureStates.OnLoading.emitToUI()
                mediaPlayer.playSound(context, R.raw.triggered) {}
                getTemperature(context)
            }
        }

        private suspend fun getTemperature(context: Context) {
            weatherRepository.nowTemperature().collect { temper ->
                when (temper) {
                    is LoadWeatherStates.OnSuccess -> {
                        TemperatureStates.OnSuccess(
                            temper.temperatureAsString
                        ).emitToUI()
                        mediaPlayer.playSound(context, temper.soundId)
                        { isDo.value = STOP_LOADING }
                    }

                    is LoadWeatherStates.OnError ->
                        mediaPlayer.playSound(context, R.raw.error)
                        { isDo.value = STOP_LOADING }
                }
            }
        }

        private val temperatureForUIAsFlow =
            MutableSharedFlow<TemperatureStates>(0, 0, BufferOverflow.SUSPEND)

        private val serviceJob: MutableState<Job> by lazy { mutableStateOf(Job()) }

        private val isDo by lazy { mutableStateOf(STOP_LOADING) }

        private val triggeringLevel get() = sPreferences.readMicrophoneLevel() * SCALE

        private suspend fun TemperatureStates.emitToUI() =
            if (localServiceBinder.isBind() == LocalServiceBindState.BINDING)
                temperatureForUIAsFlow.emit(this) else Unit

        companion object {
            const val START_LOADING = true
            const val STOP_LOADING = false
            const val SCALE = 32000
        }
    }
}