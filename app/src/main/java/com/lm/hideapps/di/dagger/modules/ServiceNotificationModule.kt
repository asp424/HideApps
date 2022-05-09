package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.notification.ServiceNotification
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ServiceNotificationModule {
	
	@Binds
	@Singleton
	fun bindsServiceNotification(serviceNotification: ServiceNotification.Base): ServiceNotification
}