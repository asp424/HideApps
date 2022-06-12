package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.res.Resources
import com.lm.hideapps.utils.StringToIntMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StringToIntMapperModule {

    @Provides
    @Singleton
    fun providesStringToIntMapper(application: Application, resources: Resources) =
        StringToIntMapper(resources, application.baseContext.packageName)
}