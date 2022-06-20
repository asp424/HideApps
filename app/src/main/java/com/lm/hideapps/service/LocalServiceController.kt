package com.lm.hideapps.service

import android.app.Application
import android.content.Intent
import com.lm.hideapps.core.SPreferences
import javax.inject.Inject


interface LocalServiceController {

    fun startMicrophoneService(onStarted: () -> Unit)

    fun stopMicrophoneService(onStopped: () -> Unit)

    fun isRunning(): LocalServiceState

    class Base @Inject constructor(
        private val application: Application,
        private val sPreferences: SPreferences,
        private val microphoneServiceIntent: Intent
    ): LocalServiceController{

        override fun startMicrophoneService(onStarted: () -> Unit) {
            if (sPreferences.serviceIsRunning() == LocalServiceState.STOPPED) {
                application.startForegroundService(microphoneServiceIntent)
                sPreferences.serviceWasRunning()
                onStarted()
            }
        }

        override fun stopMicrophoneService(onStopped: () -> Unit) {
            if (sPreferences.serviceIsRunning() == LocalServiceState.RUNNING) {
                application.stopService(microphoneServiceIntent)
                sPreferences.serviceWasStopped()
                onStopped()
            }
        }

        override fun isRunning() = sPreferences.serviceIsRunning()
    }
}