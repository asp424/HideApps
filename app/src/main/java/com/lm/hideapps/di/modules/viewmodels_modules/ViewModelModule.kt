package com.lm.hideapps.di.modules.viewmodels_modules

import androidx.lifecycle.ViewModel
import com.lm.hideapps.ui.vm.IntentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface ViewModelModule {
	@IntoMap
	@Binds
	@ViewModelKey(IntentViewModel::class)
	fun bindsBotViewModel(viewModel: IntentViewModel): ViewModel
}
