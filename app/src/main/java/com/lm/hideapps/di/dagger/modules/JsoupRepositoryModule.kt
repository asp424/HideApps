package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.JsoupRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface JsoupRepositoryModule {

    @Binds
    @Singleton
    fun bindsJsoupRepository(jsoupRepository: JsoupRepository.Base): JsoupRepository
}