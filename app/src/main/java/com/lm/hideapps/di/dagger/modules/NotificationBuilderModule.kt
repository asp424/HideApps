package com.lm.hideapps.di.dagger.modules

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.lm.hideapps.presentation.MainActivity
import com.lm.hideapps.R
import com.lm.hideapps.di.dagger.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class NotificationBuilderModule {
	
	@SuppressLint("UnspecifiedImmutableFlag")
	@Provides
	@AppScope
	fun providesNotificationBuilder(context: Application) =
		NotificationCompat.Builder(context, context.resources.getString(R.string.name))
			.setContentIntent(
		PendingIntent.getActivity(
			context, 0,
			Intent(context, MainActivity::class.java), FLAG_UPDATE_CURRENT
		)
	)
}