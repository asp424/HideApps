package com.lm.hideapps.di.dagger

import com.lm.hideapps.di.dagger.modules.*
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        NotificationManagerModule::class,
        NotificationBuilderModule::class,
        NotificationsModule::class,
        WeatherMapperModule::class,
        MicrophoneServiceUseCaseModule::class,
        SpeechRecognizeIsAvailableModule::class,
        SpeechRecognizerModule::class,
        MicrophoneRepositoryModule::class,
        WeatherRepositoryModule::class,
        PermissionsModule::class,
        ViewModelFactoryModule::class,
        MicrophoneServiceIntentModule::class,
        LocalServiceBinderModule::class,
        LocalServiceControllerModule::class
    ]
)
interface AppMapModules
