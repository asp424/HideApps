package com.lm.hideapps.di.dagger.modules
import com.lm.hideapps.sources.microphone.Microphone
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MicrophoneModule {

    @Binds
    @Singleton
    fun bindsMicrophone(microphone: Microphone.Base): Microphone
}