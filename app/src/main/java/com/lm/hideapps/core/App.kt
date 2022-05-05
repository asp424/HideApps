package com.lm.hideapps.core

import android.app.Application
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import com.lm.hideapps.di.DaggerAppComponent
import com.lm.hideapps.receiver_service.IntentReceiveService
import kotlinx.coroutines.flow.Flow

class App : Application() {
	
	private val serviceConnection: ((IntentReceiveService) -> Unit) -> ServiceConnection by lazy {
		{ onBind ->
			object : ServiceConnection {
				override fun onServiceConnected(className: ComponentName, service: IBinder) {
					onBind((service as IntentReceiveService.LocalBinder).service())
				}
				override fun onServiceDisconnected(arg0: ComponentName) {}
			}
		}
	}
	
	private val serviceIntent: ((IntentReceiveService) -> Unit) -> Intent by lazy {
		{ service ->
			Intent(baseContext, IntentReceiveService::class.java)
				.apply {
				bindService(this, serviceConnection { service(it) }, BIND_AUTO_CREATE)
			}
		}
	}
	
	private val startIntentService: (((String) -> Flow<String>) -> Unit) -> Unit
			by lazy {
				{ receiver ->
					startForegroundService(serviceIntent { receiver(it.receiver) })
				}
			}
	
	val appComponent by lazy {
		DaggerAppComponent.builder()
			.resources(resources)
			.notificationBuilder(NotificationCompat.Builder(this, getString(R.string.channel)))
			.notificationManager(NotificationManagerCompat.from(this)
			)
			.startIntentService(startIntentService)
			.create()
	}
}

