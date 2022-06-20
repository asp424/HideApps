package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.service.LocalServiceBinder
import dagger.Binds
import dagger.Module

@Module
interface LocalServiceBinderModule {

    @Binds
    @AppScope
    fun bindsLocalServiceBinder(localServiceBinder: LocalServiceBinder.Base): LocalServiceBinder
}