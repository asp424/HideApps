package com.lm.hideapps.di.dagger

import com.lm.hideapps.di.dagger.modules.*
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
	includes = [
		NotificationProviderModule::class,
		ServiceNotificationModule::class,
		ActionsNotificationModule::class,
		NotificationManagerModule::class,
		NotificationBuilderModule::class,
		ResourcesModule::class,
		ServiceControlModule::class,
		BindServiceModule::class,
		SharedPreferencesModule::class
	]
)
interface MapModule