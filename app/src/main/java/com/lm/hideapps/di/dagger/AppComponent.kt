package com.lm.hideapps.di.dagger

import android.app.Application
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.data.local_repositories.MicrophoneRepository
import com.lm.hideapps.services.IntentBroadcastReceiverService
import com.lm.hideapps.services.MicrophoneService
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
@Component(modules = [MapModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: Application): Builder

        fun create(): AppComponent
    }

    fun inject(microphoneService: MicrophoneService)
    fun inject(intentBroadcastReceiverService: IntentBroadcastReceiverService)
    fun permissions(): Permissions
    fun sPreferences(): SPreferences
    fun microphoneRepository(): MicrophoneRepository
    fun microphoneServiceControl(): ((Boolean) -> Unit) -> Unit
    fun microphoneServiceConnection(): Flow<MicrophoneService>
    fun microphoneServiceUseCase(): MicrophoneServiceUseCase
}