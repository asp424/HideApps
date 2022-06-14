package com.lm.hideapps.di.dagger.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Singleton

@Module
class CoroutineDispatcherModule {

    @Provides
    @Singleton
    fun providesCoroutineDispatcher() = IO
}