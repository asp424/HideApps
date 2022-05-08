package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.notification.NotificationProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NotificationProviderModule {
	
	@Singleton
	@Binds
	fun bindsNotificationProvider(notificationProvider: NotificationProvider.Base)
	: NotificationProvider
	
}