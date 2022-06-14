package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.local_repositories.BroadcastIntentRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface BroadcastIntentRepositoryModule {

    @Binds
    @Singleton
    fun bindsBroadcastIntentRepository(intentReceiver: BroadcastIntentRepository.Base): BroadcastIntentRepository

}