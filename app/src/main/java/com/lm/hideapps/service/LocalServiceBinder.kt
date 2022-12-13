package com.lm.hideapps.service

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.lm.hideapps.core.log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface LocalServiceBinder {

    fun connect(serviceConnection: ServiceConnection)

    fun disconnect(serviceConnection: ServiceConnection)

    fun bind()

    fun unBind()

    fun isBind(): LocalServiceBindState

    fun serviceConnection(onConnect: (LocalService) -> Unit): ServiceConnection

    fun bindToLocalService(): Flow<LocalService>

    class Base @Inject constructor(
        private val application: Application,
        private val microphoneServiceIntent: Intent
    ) : LocalServiceBinder {

        override fun bindToLocalService() = callbackFlow {
            serviceConnection { trySendBlocking(it) }
                .apply {
                    connect(this)
                    awaitClose { disconnect(this) }
                }
        }.flowOn(IO)

        override fun connect(serviceConnection: ServiceConnection) {

            if (connectionState.value == LocalServiceConnectionState.DISCONNECTED) {
                application.bindService(
                    microphoneServiceIntent, serviceConnection, Application.BIND_AUTO_CREATE
                )
            }
        }

        override fun disconnect(serviceConnection: ServiceConnection) {

            if (connectionState.value == LocalServiceConnectionState.CONNECTED) {
                application.unbindService(serviceConnection)
                connectionState.value = LocalServiceConnectionState.DISCONNECTED
            }
        }

        override fun bind() { bindingState.value = LocalServiceBindState.BINDING }

        override fun unBind() { bindingState.value = LocalServiceBindState.UNBINDING }

        override fun isBind() = bindingState.value

        override fun serviceConnection(onConnect: (LocalService) -> Unit)
        = object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

                if (connectionState.value == LocalServiceConnectionState.DISCONNECTED) {

                    onConnect((service as LocalService.LocalBinder).service())
                    connectionState.value = LocalServiceConnectionState.CONNECTED
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                connectionState.value = LocalServiceConnectionState.DISCONNECTED
            }
        }

        private val connectionState by lazy {
            mutableStateOf(LocalServiceConnectionState.DISCONNECTED)
        }

        private val bindingState by lazy {
            mutableStateOf(LocalServiceBindState.UNBINDING)
        }
    }
}