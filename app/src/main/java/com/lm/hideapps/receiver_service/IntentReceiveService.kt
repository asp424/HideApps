package com.lm.hideapps.receiver_service

import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_MIN
import android.app.Service
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.lm.hideapps.core.App
import com.lm.hideapps.data.sources.IntentReceiver
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
	
	@Inject
	lateinit var notificationProvider: NotificationProvider
	
	@Inject
	lateinit var intentReceiver: IntentReceiver
	
	private fun receiver(actions: List<String>) = callbackFlow {
		intentReceiver.broadcastReceiver { trySendBlocking(it) }.apply {
			actions.forEach {
				registerReceiver(this, IntentFilter(it))
			}
			awaitClose { unregisterReceiver(this) }
		}
	}.flowOn(IO)
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		(applicationContext as App).appComponent.inject(this)
		startForeground(101, notificationProvider.serviceNotification())
		job = collectActions()
		
		return START_NOT_STICKY
	}
	
	override fun onBind(intent: Intent?): IBinder = binder
	
	inner class LocalBinder : Binder() {
		fun service(): IntentReceiveService = this@IntentReceiveService
	}
	
	override fun onDestroy() {
		super.onDestroy()
		job.cancel()
	}
	
	private fun collectActions() = CoroutineScope(IO).launch {
		listActions.apply {
			receiver(this).collect { action ->
				notificationProvider.actionNotification(action)
			}
		}
	}
	
	private val message by lazy { "Service is running" }
	
	companion object {
		val listActions =
			listOf(ACTION_POWER_CONNECTED, ACTION_HEADSET_PLUG, ACTION_POWER_DISCONNECTED)
	}
}