package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.service.LocalServiceBinder
import com.lm.hideapps.service.LocalServiceController
import dagger.Binds
import dagger.Module

@Module
interface LocalServiceControllerModule {

    @Binds
    @AppScope
    fun bindsLocalServiceController(
        localServiceController: LocalServiceController.Base
    ): LocalServiceController
}