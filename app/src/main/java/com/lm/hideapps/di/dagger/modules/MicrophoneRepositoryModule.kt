package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.di.dagger.scopes.AppScope
import dagger.Binds
import dagger.Module

@Module
interface MicrophoneRepositoryModule {

    @Binds
    @AppScope
    fun bindsMicrophoneRepository(
        microphoneRepository: MicrophoneRepository.Base
    ): MicrophoneRepository
}