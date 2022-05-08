package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.content.Intent
import com.lm.hideapps.receiver_service.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceControlModule {
	
	@Provides
	@Singleton
	fun providesServiceControl(
		application: Application, sharedPrefProvider: SharedPrefProvider,
	): ((Boolean) -> Unit) -> Unit =
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

