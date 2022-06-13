package com.lm.hideapps.di.dagger.modules

import android.content.res.Resources
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.core.StringToIntMapper
import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.data.remote_repositories.WeatherRepository
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MicrophoneServiceUseCaseModule {

    @Provides
    @Singleton
    fun providesMicrophoneServiceUseCase(
        microphoneRepository: MicrophoneRepository,
        weatherRepository: WeatherRepository,
        mapper: StringToIntMapper,
        sPreferences: SPreferences,
        resources: Resources
    ) = MicrophoneServiceUseCase(
        microphoneRepository,
        weatherRepository,
        mapper,
        sPreferences,
        resources
    )
}