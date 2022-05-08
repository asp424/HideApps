package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.notification.ServiceNotificationInteractor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ServiceNotificationInteractorModule {
	
	@Binds
	@Singleton
	fun bindsServiceNotificationInteractor(
		serviceNotificationInteractor: ServiceNotificationInteractor.Base)
	: ServiceNotificationInteractor
	
}