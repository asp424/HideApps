package com.lm.hideapps.di.dagger

import com.lm.hideapps.di.dagger.modules.*
import dagger.Module

@Module(
	includes = [
		NotificationsModule::class,
		NotificationManagerModule::class,
		NotificationBuilderModule::class,
		ResourcesModule::class,
		IntentReceiveServiceControlModule::class,
		IntentReceiveServiceConnectionModule::class,
		SPreferencesModule::class,
		PermissionsModule::class,
		MicrophoneServiceControlModule::class,
		MicrophoneServiceConnectionModule::class,
		StringToIntMapperModule::class,
		SpeechRecognizeIsAvailableModule::class,
		SpeechRecognizerModule::class,
		NotificationsModule::class,
		MicrophoneServiceUseCaseModule::class
	]
)
interface MapModule