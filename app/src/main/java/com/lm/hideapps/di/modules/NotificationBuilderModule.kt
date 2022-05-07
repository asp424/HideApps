package com.lm.hideapps.di.modules

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.MainActivity
import com.lm.hideapps.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationBuilderModule {
	
	@SuppressLint("UnspecifiedImmutableFlag")
	@Provides
	@Singleton
	fun bindsNotificationBuilder(application: Application) = NotificationCompat.Builder(application,
		application.resources.getString(
		R.string.channel)).setContentIntent(
		PendingIntent.getActivity(
			application, 0,
			Intent(application, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
		)
	)
}