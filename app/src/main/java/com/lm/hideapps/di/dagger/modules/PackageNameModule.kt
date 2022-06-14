package com.lm.hideapps.di.dagger.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PackageNameModule {

    @Provides
    @Singleton
    fun providesPackageName(application: Application): String = application.packageName
}
