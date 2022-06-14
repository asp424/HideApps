package com.lm.hideapps.di.dagger

import com.lm.hideapps.di.dagger.modules.*
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
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
        WeatherMapperModule::class,
        SpeechRecognizeIsAvailableModule::class,
        SpeechRecognizerModule::class,
        NotificationsModule::class,
        ViewModelFactoryModule::class,
        BroadcastIntentRepositoryModule::class,
        WeatherMapperModule::class,
        PackageNameModule::class,
        MicrophoneServiceUseCaseModule::class,
        CoroutineDispatcherModule::class
    ]
)
interface MapModule