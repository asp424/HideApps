package com.lm.hideapps.receiver_service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.Binder
import com.lm.hideapps.core.appComponent
import com.lm.hideapps.notification.NotificationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntentReceiveService : Service() {
	
	private val binder = LocalBinder()
	
	private var job: Job = Job()
	
	private fun broadcastReceiver(onReceive: (String) -> Unit) =
		object : BroadcastReceiver() {
			override fun onReceive(context: Context?, intent: Intent?) {
				onReceive(intent?.action.toString())
			}
		}
	
	fun receiver(actions: List<String>) = callbackFlow {
		broadcastReceiver { trySendBlocking(it) }.also { receiver ->
			actions.forEach { registerReceiver(receiver, IntentFilter(it)) }
			awaitClose { unregisterReceiver(receiver) }
		}
	}.flowOn(IO)
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		startForeground(101, appComponent.notificationProvider().serviceNotification())
		collectActions()
		return START_NOT_STICKY
	}
	
	override fun onBind(intent: Intent?) = binder
	
	inner class LocalBinder : Binder() {
		fun service(): IntentReceiveService = this@IntentReceiveService
	}
	
	override fun onDestroy() {
		super.onDestroy()
		job.cancel()
	}
	
	private fun collectActions() = CoroutineScope(IO).launch {
		receiver(actions).collect { action ->
			appComponent.notificationProvider().actionNotification(action)
		}
	}.apply { job = this }
	
	private val actions by lazy {
		listOf(ACTION_POWER_CONNECTED, ACTION_HEADSET_PLUG, ACTION_POWER_DISCONNECTED)
	}
}

