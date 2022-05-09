package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.notification.ActionsNotification
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface ActionsNotificationModule {
	
	@Binds
	@Singleton
	fun bindsActionsNotification(actionsNotification: ActionsNotification.Base): ActionsNotification
}