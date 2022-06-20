package com.lm.hideapps.di.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.presentation.view_models.MainViewModel
import com.lm.hideapps.presentation.view_models.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.reflect.KClass

@ExperimentalCoroutinesApi
@Module(includes = [ViewModelModules::class])
interface ViewModelFactoryModule {

    @Binds
    @AppScope
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@ExperimentalCoroutinesApi
@Module
interface ViewModelModules {
    @IntoMap
    @Binds
    @AppScope
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
