package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.remote_repositories.WeatherMapper
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface WeatherMapperModule {

    @Binds
    @Singleton
    fun bindsWeatherMapper(weatherMapper: WeatherMapper.Base): WeatherMapper
}