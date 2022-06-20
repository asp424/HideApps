package com.lm.hideapps.core

import android.content.SharedPreferences
import com.lm.hideapps.service.LocalServiceState
import javax.inject.Inject


interface SPreferences {

    fun serviceWasRunning(): LocalServiceState

    fun serviceWasStopped(): LocalServiceState

    fun serviceIsRunning(): LocalServiceState

    fun saveMicrophoneLevel(level: Float)

    fun readMicrophoneLevel(): Float

    class Base @Inject constructor(
        private val sharedPreferences: SharedPreferences,
    ) : SPreferences {

        override fun serviceWasRunning(): LocalServiceState {
            sharedPreferences
                .edit()
                .putBoolean(SERVICE_RUNNING_STATE_KEY, true)
                .apply()
            return LocalServiceState.RUNNING
        }

        override fun serviceWasStopped(): LocalServiceState {
            sharedPreferences
                .edit()
                .putBoolean(SERVICE_RUNNING_STATE_KEY, false)
                .apply()
            return LocalServiceState.STOPPED
        }

        override fun serviceIsRunning() = with(sharedPreferences
            .getBoolean(SERVICE_RUNNING_STATE_KEY, false)){
            if (this) LocalServiceState.RUNNING else LocalServiceState.STOPPED
        }

        override fun saveMicrophoneLevel(level: Float) = sharedPreferences
            .edit()
            .putFloat(MICROPHONE_LEVEL_KEY, level)
            .apply()

        override fun readMicrophoneLevel() = sharedPreferences
            .getFloat(MICROPHONE_LEVEL_KEY, DEFAULT_MICROPHONE_LEVEL)

        companion object {
            const val SERVICE_RUNNING_STATE_KEY = "serviceRunningState"
            const val MICROPHONE_LEVEL_KEY = "microphoneLevel"
            const val DEFAULT_MICROPHONE_LEVEL = 0.5f
        }
    }
}