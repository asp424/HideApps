package com.lm.hideapps.use_cases

import android.content.Context
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.lm.hideapps.R
import com.lm.hideapps.core.MediaPlayerProvider
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.data.remote_repositories.WeatherMapper
import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.data.remote_repositories.LoadWeatherStates
import com.lm.hideapps.data.remote_repositories.WeatherRepository
import com.lm.hideapps.notifications.Notifications
import com.lm.hideapps.services.MicrophoneService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MicrophoneServiceUseCase {

    fun onBind()

    fun onUnbind()

    fun onStartCommand(context: MicrophoneService)

    fun startServiceJob(onLaunch: suspend CoroutineScope.() -> Unit)

    fun cancelServiceJob()

    fun temperatureForUIAsFlow(): SharedFlow<String>

    class Base @Inject constructor(
        private val microphoneRepository: MicrophoneRepository,
        private val weatherRepository: WeatherRepository,
        private val sPreferences: SPreferences,
        private val notifications: Notifications,
        private val mediaPlayer: MediaPlayerProvider,
        private val serviceDispatcher: CoroutineDispatcher,
        private val stringConverter: WeatherMapper
    ) : MicrophoneServiceUseCase {

        override fun onStartCommand(context: MicrophoneService) {
            context.startForeground(101, notifications.showForegroundNotification())
            startServiceJob {
                    microphoneRepository.microphoneLevelAsFlow(
                        8000, CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT
                    ).collect { onGetLevel(it, context) }
            }
        }

        override fun startServiceJob(
            onLaunch: suspend CoroutineScope.() -> Unit
        ) {
            cancelServiceJob()
            serviceJob.value = CoroutineScope(serviceDispatcher)
                .launch { onLaunch(this) }
        }

        override fun onBind() { isBind.value = BIND }

        override fun onUnbind() { isBind.value = UNBIND }

        override fun cancelServiceJob() = serviceJob.value.cancel()

        override fun temperatureForUIAsFlow() = temperatureForUIAsFlow.asSharedFlow()

        private suspend fun onGetLevel(level: Int, context: Context) {
            if (level > triggeringLevel && !isDo.value) {
                isDo.value = START_LOADING
                START_LOADING.toString().emitToUI()
                mediaPlayer.playSound(context, R.raw.triggered) {}
                getTemperature(context)
            }
        }

        private suspend fun getTemperature(context: Context) {
            weatherRepository.nowTemperature().collect { temperature ->
                when(temperature){
                    is LoadWeatherStates.OnSuccess -> {
                        temperature.temperature.emitToUI()
                        mediaPlayer.playSound(context, temperature.id)
                        { isDo.value = STOP_LOADING }
                    }

                    is LoadWeatherStates.OnError -> mediaPlayer.playSound(context, R.raw.error)
                    { isDo.value = STOP_LOADING }
                }
            }
        }

        private val temperatureForUIAsFlow =
            MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

        private val isBind by lazy { mutableStateOf(STOP_LOADING) }

        private val serviceJob: MutableState<Job> by lazy { mutableStateOf(Job()) }

        private val isDo by lazy { mutableStateOf(STOP_LOADING) }

        private val triggeringLevel get() = sPreferences.readLevel() * SCALE

        private suspend fun String.emitToUI() =
            if (isBind.value) temperatureForUIAsFlow.emit(this) else Unit

        companion object {
            const val START_LOADING = true
            const val BIND = true
            const val STOP_LOADING = false
            const val UNBIND = false
            const val SCALE = 32000
        }
    }
}