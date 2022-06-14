package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MicrophoneServiceUseCaseModule {

    @Binds
    @Singleton
    fun bindsMicrophoneServiceUseCase(
        microphoneServiceUseCase: MicrophoneServiceUseCase.Base
    ): MicrophoneServiceUseCase
}