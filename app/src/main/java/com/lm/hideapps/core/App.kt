package com.lm.hideapps.core

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import com.lm.hideapps.di.DaggerAppComponent
import com.lm.hideapps.receiver_service.IntentReceiveService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class App : Application() {
	
	private var mBinder = false
	
	private val startIntent by lazy { Intent(this, IntentReceiveService::class.java) }
	
	private val controlIntentServer
		get() = callbackFlow {
			startForegroundService(startIntent)
			var mBinder: Boolean
			object : ServiceConnection {
				override fun onServiceConnected(className: ComponentName, service: IBinder) {
					trySendBlocking(
						((service as IntentReceiveService.LocalBinder).service()))
				}
				override fun onServiceDisconnected(arg0: ComponentName) {}
			}.apply {
				mBinder = bindService(startIntent, this, BIND_AUTO_CREATE)
				awaitClose { if (mBinder) unbindService(this); stopService(startIntent) }
			}
		}.flowOn(IO)
	
	val appComponent by lazy {
		DaggerAppComponent.builder()
			.resources(resources)
			.notificationBuilder(NotificationCompat.Builder(this, getString(R.string.channel)))
			.notificationManager(NotificationManagerCompat.from(this))
			.controlIntentService(controlIntentServer)
			.create()
	}
}


