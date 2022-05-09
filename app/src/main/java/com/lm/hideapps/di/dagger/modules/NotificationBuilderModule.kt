package com.lm.hideapps.di.dagger.modules

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import androidx.core.app.NotificationCompat
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
	fun providesNotificationBuilder(application: Application) = NotificationCompat.Builder(
		application,
		application.resources.getString(
			R.string.name
		)
	).setContentIntent(
		PendingIntent.getActivity(
			application, 0,
			Intent(application, MainActivity::class.java), FLAG_UPDATE_CURRENT
		)
	)
}