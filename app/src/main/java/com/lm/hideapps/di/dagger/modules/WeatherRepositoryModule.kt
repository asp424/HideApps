package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.remote_repositories.WeatherRepository
import com.lm.hideapps.di.dagger.scopes.AppScope
import dagger.Binds
import dagger.Module

@Module
interface WeatherRepositoryModule {

    @Binds
    @AppScope
    fun bindsWeatherRepository(weatherRepository: WeatherRepository.Base): WeatherRepository
}