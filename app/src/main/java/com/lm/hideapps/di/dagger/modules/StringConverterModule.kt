package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.core.WeatherMapper
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface StringConverterModule {

    @Binds
    @Singleton
    fun bindsStringToSoundIdMapper(stringConverter: WeatherMapper.Base): WeatherMapper
}