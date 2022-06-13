package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.Intent
import com.lm.hideapps.services.IntentBroadcastReceiverService
import com.lm.hideapps.core.SPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IntentReceiveServiceControlModule {
	
	@Provides
	@Singleton
	fun providesIntentReceiveServiceControl(
		application: Application, sharedPrefProvider: SPreferences,
	): ((Boolean) -> Unit) -> Intent =
		{
			Intent(application, IntentBroadcastReceiverService::class.java)
				.also { intent ->
					if (!sharedPrefProvider.isRunning()) {
						application.startForegroundService(intent); it(sharedPrefProvider.run())
					} else {
						application.stopService(intent); it(sharedPrefProvider.stop())
					}
				}
		}
}

