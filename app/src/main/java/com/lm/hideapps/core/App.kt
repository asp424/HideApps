package com.lm.hideapps.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.lm.hideapps.R
import com.lm.hideapps.di.DaggerAppComponent
import com.lm.hideapps.receiver_service.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider

@SuppressLint("UnspecifiedImmutableFlag")
class App : Application() {
	
	private var bound = false
	private lateinit var serviceConnection: ServiceConnection
	
	private lateinit var intentReceiveService: IntentReceiveService
	
	private val startServiceIntent by lazy {
		Intent(this, IntentReceiveService::class.java)
	}
	
	private val sharedPref by lazy {
		SharedPrefProvider
			.Base(getSharedPreferences(getString(R.string.name), MODE_PRIVATE))
	}
	
	val appComponent by lazy {
		DaggerAppComponent.builder()
			.context(this)
			.sharedPreferences(sharedPref)
			.serviceControl(serviceControl)
			.bindService(bindService)
			.create()
	}
	
	private val serviceControl: ((Boolean) -> Unit) -> Unit by lazy {
		{
			startServiceIntent.also { intent ->
				if (!sharedPref.isRunning()) {
					startForegroundService(intent); it(sharedPref.run())
				} else {
					stopService(intent); it(sharedPref.stop())
				}
			}
		}
	}
	
	private val bindService by lazy {
		{ if (!bound) bound = bindService(startServiceIntent, serviceConnection, BIND_AUTO_CREATE)
		else unbindService(serviceConnection); bound = false }
	}
	
	override fun onCreate() {
		super.onCreate()
		serviceConnection = object : ServiceConnection {
			override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
				intentReceiveService = (service as IntentReceiveService.LocalBinder).service()
				bound = true
			}
			
			override fun onServiceDisconnected(name: ComponentName?) { bound = false }
		}
	}
}




