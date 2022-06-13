package com.lm.hideapps.services

import android.app.Service
import android.content.Intent
import android.os.Binder

class IntentBroadcastReceiverService : Service() {
    private var bind = false

    /*

    private var job: Job = Job()

    private val intentFlow =
    MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

    val actionFlow get() = intentFlow.asSharedFlow()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    appComponent.notificationProvider().apply {

    startForeground(101, showForegroundNotification())

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
    BroadcastIntentReceiver { trySendBlocking(it) }.also { receiver ->
    actions.forEach { registerReceiver(receiver, IntentFilter(it)) }
    awaitClose { unregisterReceiver(receiver) }
    }
    }.flowOn(IO)

    private val actions by lazy {
    listOf(ACTION_POWER_CONNECTED, ACTION_HEADSET_PLUG, ACTION_POWER_DISCONNECTED)
    }



    override fun onUnbind(intent: Intent?): Boolean {
    bind = false
    return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
    super.onRebind(intent)
    bind = true
    }



    override fun onDestroy() {
    super.onDestroy()
    job.cancel()
    }
    }
     */
    inner class LocalBinder : Binder() {
        fun service(): IntentBroadcastReceiverService = this@IntentBroadcastReceiverService
    }
    override fun onBind(intent: Intent?) = LocalBinder().apply { bind = true }
}