package com.lm.hideapps.di

import com.lm.hideapps.di.modules.IntentReceiverModule
import com.lm.hideapps.di.modules.NotificationProviderModule
import com.lm.hideapps.di.modules.viewmodels_modules.ViewModelFactoryModule
import com.lm.hideapps.di.modules.viewmodels_modules.ViewModelModule
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(includes = [
	NotificationProviderModule::class,
	ViewModelFactoryModule::class,
	ViewModelModule::class,
	IntentReceiverModule::class
])
interface MapModule