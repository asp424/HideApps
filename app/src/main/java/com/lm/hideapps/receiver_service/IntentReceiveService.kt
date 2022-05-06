package com.lm.hideapps.receiver_service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.lm.hideapps.core.App
import com.lm.hideapps.data.sources.IntentReceiver
import com.lm.hideapps.notification.NotificationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IntentReceiveService : Service() {
	
	val scope = CoroutineScope(IO)
	
	@Inject
	lateinit var notificationProvider: NotificationProvider
	
	@Inject
	lateinit var intentReceiver: IntentReceiver
	
	private fun broadcastReceiver(onReceive: (String) -> Unit) =
		object : BroadcastReceiver() {
			override fun onReceive(context: Context?, intent: Intent?) {
				onReceive(intent?.action.toString())
			}
		}
	
	fun receiver(actions: List<String>) = callbackFlow {
		broadcastReceiver { trySendBlocking(it) }.apply {
			actions.forEach {
				registerReceiver(this, IntentFilter(it))
			}
			awaitClose { unregisterReceiver(this) }
		}
	}.flowOn(IO)
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		(applicationContext as App).appComponent.inject(this)
		startForeground(101, notificationProvider.notification("Service is running", false))
		return START_NOT_STICKY
	}
	
	override fun onBind(intent: Intent?): IBinder = LocalBinder()
	
	inner class LocalBinder : Binder() {
		fun service() = this@IntentReceiveService
	}
}