package com.lm.hideapps.di.dagger

import android.app.Application
import com.lm.hideapps.core.ResourcesManager
import com.lm.hideapps.core.SPreferences
import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.presentation.MainActivity
import com.lm.hideapps.service.LocalService
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase
import dagger.BindsInstance
import dagger.Component

@[Component(modules = [AppMapModules::class]) AppScope]
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun resourcesManager(resourcesManager: ResourcesManager): Builder

        @BindsInstance
        fun context(application: Application): Builder

        @BindsInstance
        fun sharedPreferences(sPreferences: SPreferences): Builder

        fun build(): AppComponent
    }

    fun sPreferences(): SPreferences
    fun microphoneServiceUseCase(): MicrophoneServiceUseCase
    fun inject(localService: LocalService)
    fun inject(mainActivity: MainActivity)
}