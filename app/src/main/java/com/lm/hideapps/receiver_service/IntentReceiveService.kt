package com.lm.hideapps.receiver_service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.lm.hideapps.core.App
import com.lm.hideapps.notification.NotificationProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IntentReceiveService : Service() {
	
	private val broadcastReceiver: ((String) -> Unit) -> BroadcastReceiver by lazy {
		{ onReceive ->
			object : BroadcastReceiver() {
				override fun onReceive(context: Context?, intent: Intent?) {
					onReceive(intent?.action.toString())
				}
			}
		}
	}
	
	val receiver: (String) -> Flow<String>
		get() = {
			callbackFlow {
				broadcastReceiver { trySendBlocking(it) }
					.apply {
					registerReceiver(this, IntentFilter(it))
					awaitClose {
						unregisterReceiver(this)
						notificationProvider.hideNotification()
						stopSelf()
					}
				}
			}.flowOn(IO)
		}
	
	@Inject
	lateinit var notificationProvider: NotificationProvider
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		
		(applicationContext as App).appComponent.inject(this)
		
		startForeground(101, notificationProvider.showNotification())
		
		return START_NOT_STICKY
	}
	
	override fun onBind(intent: Intent?): IBinder = LocalBinder()
	
	inner class LocalBinder : Binder() { fun service() = this@IntentReceiveService }
}