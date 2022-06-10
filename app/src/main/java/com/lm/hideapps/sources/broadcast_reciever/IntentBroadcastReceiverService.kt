package com.lm.hideapps.sources.broadcast_reciever

import android.app.Service
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.Binder
import com.lm.hideapps.R
import com.lm.hideapps.core.AppComponentGetter.appComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class IntentBroadcastReceiverService : Service() {

    private var bind = false

    private var job: Job = Job()

    private val intentFlow =
        MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

    val actionFlow get() = intentFlow.asSharedFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        appComponent.notificationProvider().apply {

            startForeground(101, serviceNotification())

            job.cancel(); job = CoroutineScope(IO).launch {


            receiver(actions).collect { action ->

                if (!bind) actionNotification(action)
                CoroutineScope(IO).launch {
                    intentFlow.emit(action.substringAfter(getString(R.string.substring))); cancel()
                }
            }
        }
            return START_NOT_STICKY
        }
    }

    private suspend fun receiver(actions: List<String>) = callbackFlow {
        IntentBroadcastReceiver { trySendBlocking(it) }.also { receiver ->
            actions.forEach { registerReceiver(receiver, IntentFilter(it)) }
            awaitClose { unregisterReceiver(receiver) }
        }
    }.flowOn(IO)

    private val actions by lazy {
        listOf(ACTION_POWER_CONNECTED, ACTION_HEADSET_PLUG, ACTION_POWER_DISCONNECTED)
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
        fun service(): IntentBroadcastReceiverService = this@IntentBroadcastReceiverService
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

