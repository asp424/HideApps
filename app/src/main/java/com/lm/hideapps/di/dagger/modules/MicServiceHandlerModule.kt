package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.sources.microphone.MicServiceHandler
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MicServiceHandlerModule {

    @Binds
    @Singleton
    fun bindsMicServiceHandler(micServiceHandler: MicServiceHandler.Base): MicServiceHandler
}