package com.lm.hideapps.di

import com.lm.hideapps.di.modules.*
import com.lm.hideapps.di.modules.viewmodels_modules.ViewModelFactoryModule
import com.lm.hideapps.di.modules.viewmodels_modules.ViewModelModule
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
	includes = [
		NotificationProviderModule::class,
		ViewModelFactoryModule::class,
		ViewModelModule::class,
		IntentReceiverModule::class,
		ServiceNotificationInteractorModule::class,
		ActionsNotificationInteractorModule::class
	]
)
interface MapModule