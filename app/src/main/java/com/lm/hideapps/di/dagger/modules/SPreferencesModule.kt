package com.lm.hideapps.di.dagger.modules

import android.app.Application
import com.lm.hideapps.R
import com.lm.hideapps.core.SPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SPreferencesModule {
	
	@Provides
	@Singleton
	fun providesSPreferences(application: Application): SPreferences =
		SPreferences.Base(
				application.getSharedPreferences(
					application.getString(R.string.name),
					Application.MODE_PRIVATE
				)
			)
}