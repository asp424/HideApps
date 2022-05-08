package com.lm.hideapps.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourcesModule {
	
	@Provides
	@Singleton
	fun providesResources(application: Application) = application.resources
}