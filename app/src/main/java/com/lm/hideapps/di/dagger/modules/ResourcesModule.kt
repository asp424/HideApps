package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourcesModule {
	
	@Provides
	@Singleton
	fun providesResources(application: Application): Resources = application.resources
}