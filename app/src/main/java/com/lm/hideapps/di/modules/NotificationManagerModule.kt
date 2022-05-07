package com.lm.hideapps.di.modules

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.data.sources.IntentReceiver
import com.lm.hideapps.data.sources.IntentReceiverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationManagerModule {
	
	
	@Provides
	@Singleton
	fun bindsNotificationManager(application: Application) = NotificationManagerCompat.from(application)
	
}