package com.lm.hideapps.di.dagger

import android.app.Application
import android.content.Intent
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.sources.microphone.Microphone
import com.lm.hideapps.notification.NotificationProvider
import com.lm.hideapps.services.IntentReceiveService
import com.lm.hideapps.services.SnoreService
import com.lm.hideapps.shared_pref.SharedPrefProvider
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
@Component(modules = [MapModule::class])
interface AppComponent {
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun context(application: Application): Builder
		
		fun create(): AppComponent
	}
	
	fun sharedPreferences(): SharedPrefProvider
	fun intentReceiveServiceControl(): ((Boolean) -> Unit) -> Intent
	fun snoreServiceControl(): ((Boolean) -> Unit) -> Unit
	fun bindIntentReceiveService(): Flow<IntentReceiveService>
	fun bindSnoreService(): Flow<SnoreService>
	fun notificationProvider(): NotificationProvider
	fun microphone(): Microphone
	fun permissions(): Permissions
}