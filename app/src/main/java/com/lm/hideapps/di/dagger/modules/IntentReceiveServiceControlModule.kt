package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.Intent
import com.lm.hideapps.services.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IntentReceiveServiceControlModule {
	
	@Provides
	@Singleton
	fun providesIntentReceiveServiceControl(
		application: Application, sharedPrefProvider: SharedPrefProvider,
	): ((Boolean) -> Unit) -> Intent =
		{
			Intent(application, IntentReceiveService::class.java)
				.also { intent ->
					if (!sharedPrefProvider.isRunning()) {
						application.startForegroundService(intent); it(sharedPrefProvider.run())
					} else {
						application.stopService(intent); it(sharedPrefProvider.stop())
					}
				}
		}
}

