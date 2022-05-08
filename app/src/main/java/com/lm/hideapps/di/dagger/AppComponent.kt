package com.lm.hideapps.di.dagger

import android.app.Application
import com.lm.hideapps.MainActivity
import com.lm.hideapps.receiver_service.IntentReceiveService
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
	
	fun inject(intentReceiveService: IntentReceiveService)
	fun sharedPreferences(): SharedPrefProvider
	fun serviceControl(): ((Boolean) -> Unit) -> Unit
	fun bindService(): Flow<IntentReceiveService>
}