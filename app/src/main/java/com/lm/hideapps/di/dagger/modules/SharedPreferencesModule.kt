package com.lm.hideapps.di.dagger.modules

import android.app.Application
import com.lm.hideapps.R
import com.lm.hideapps.shared_pref.SharedPrefProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
	
	@Provides
	@Singleton
	fun providesSharedPreferences(application: Application): SharedPrefProvider =
		SharedPrefProvider.Base(
				application.getSharedPreferences(
					application.getString(R.string.name),
					Application.MODE_PRIVATE
				)
			)
}