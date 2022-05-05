package com.lm.hideapps.di.modules.viewmodels_modules

import androidx.lifecycle.ViewModelProvider
import com.lm.hideapps.ui.vm.ViewModelFactory
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(includes = [ViewModelModule::class])
interface ViewModelFactoryModule {
	
	@Binds
	fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}