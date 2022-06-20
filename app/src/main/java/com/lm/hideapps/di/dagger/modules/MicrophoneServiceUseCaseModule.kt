package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import dagger.Binds
import dagger.Module

@Module
interface MicrophoneServiceUseCaseModule {

    @Binds
    @AppScope
    fun bindsMicrophoneServiceUseCase(
        microphoneServiceUseCase: MicrophoneServiceUseCase.Base
    ): MicrophoneServiceUseCase
}