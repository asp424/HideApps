package com.lm.hideapps.core

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.MainActivity
import com.lm.hideapps.R
import com.lm.hideapps.di.DaggerAppComponent
import com.lm.hideapps.receiver_service.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider

@SuppressLint("UnspecifiedImmutableFlag")
class App : Application() {
	
	private val startIntent by lazy { Intent(this, IntentReceiveService::class.java) }
	
	private val sharedPref by lazy {
		SharedPrefProvider
			.Base(getSharedPreferences(getString(R.string.nameShared), MODE_PRIVATE))
	}
	
	private val serviceControl: ((Boolean) -> Unit) -> Unit by lazy {
		{
			if (!sharedPref.isRunning()) {
				startForegroundService(startIntent); it(sharedPref.run())
			} else {
				stopService(startIntent); it(sharedPref.stop())
			}
		}
	}
	
	val appComponent by lazy {
		DaggerAppComponent.builder()
			.notificationBuilder(NotificationCompat.Builder(this, getString(R.string.channel)))
			.notificationManager(NotificationManagerCompat.from(this))
			.notificationIntent(
				getActivity(
					this, 0,
					Intent(this, MainActivity::class.java), FLAG_UPDATE_CURRENT
				)
			)
			.sharedPreferences(sharedPref)
			.serviceControl(serviceControl)
			.create()
	}
}



