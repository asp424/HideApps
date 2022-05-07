package com.lm.hideapps.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import com.lm.hideapps.R
import com.lm.hideapps.di.DaggerAppComponent
import com.lm.hideapps.receiver_service.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider

@SuppressLint("UnspecifiedImmutableFlag")
class App : Application() {
	
	private val sharedPref by lazy {
		SharedPrefProvider
			.Base(getSharedPreferences(getString(R.string.nameShared), MODE_PRIVATE))
	}
	
	val appComponent by lazy {
		DaggerAppComponent.builder()
			.context(this)
			.sharedPreferences(sharedPref)
			.serviceControl(serviceControl)
			.create()
	}
	
	private val serviceControl: ((Boolean) -> Unit) -> Unit by lazy {
		{
			Intent(this, IntentReceiveService::class.java).also { intent ->
				if (!sharedPref.isRunning()) {
					startForegroundService(intent); it(sharedPref.run())
				} else {
					stopService(intent); it(sharedPref.stop())
				}
			}
		}
	}
}



