package com.lm.hideapps.di.dagger

import com.lm.hideapps.di.dagger.modules.*
import dagger.Module

@Module(
	includes = [
		NotificationProviderModule::class,
		ServiceNotificationModule::class,
		ActionsNotificationModule::class,
		NotificationManagerModule::class,
		NotificationBuilderModule::class,
		ResourcesModule::class,
		IntentReceiveServiceControlModule::class,
		BindIntentReceiveServiceModule::class,
		SPreferencesModule::class,
		PermissionsModule::class,
		MicrophoneModule::class,
		SnoreServiceControlModule::class,
		BindSnoreServiceModule::class,
		PlaySoundModule::class,
		JsoupRepositoryModule::class,
		StringToIntMapperModule::class,
		MicServiceHandlerModule::class,
		SpeechRecognizeIsAvailableModule::class,
		SpeechRecognizerCreatorModule::class,
		SpeechRecognizeProviderModule::class
	]
)
interface MapModule