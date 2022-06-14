package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.lm.hideapps.services.IntentBroadcastReceiverService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Module
class IntentReceiveServiceConnectionModule {
	
	@Provides
	@Singleton
	fun providesBindService(application: Application) = callbackFlow {
		var bound = false
		object : ServiceConnection {
			override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
				trySendBlocking(
					(service as IntentBroadcastReceiverService.LocalBinder).service()
				)
				bound = true
			}
			
			override fun onServiceDisconnected(name: ComponentName?) {
				bound = false
			}
		}.apply {
			if (!bound) bound = application.bindService(
				Intent(application, IntentBroadcastReceiverService::class.java), this,
				BIND_AUTO_CREATE
			)
			awaitClose { if (bound) application.unbindService(this)
				bound = false
			cancel()
			}
		}
	}
}