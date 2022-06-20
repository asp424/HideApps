package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.remote_repositories.WeatherMapper
import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.di.dagger.scopes.MicrophoneServiceScope
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface WeatherMapperModule {

    @Binds
    @AppScope
    fun bindsWeatherMapper(weatherMapper: WeatherMapper.Base): WeatherMapper
}