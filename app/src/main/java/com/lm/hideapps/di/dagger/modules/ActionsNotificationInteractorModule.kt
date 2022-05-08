package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.notification.ActionsNotificationInteractor
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface ActionsNotificationInteractorModule {
	
	@Binds
	@Singleton
	fun bindsActionsNotification(
		actionsNotification: ActionsNotificationInteractor.Base): ActionsNotificationInteractor
}