package com.lm.hideapps.di.dagger

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.sources.microphone.Microphone
import com.lm.hideapps.notification.NotificationProvider
import com.lm.hideapps.sources.broadcast_reciever.IntentBroadcastReceiverService
import com.lm.hideapps.sources.microphone.MicrophoneService
import com.lm.hideapps.core.SPreferences
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
	
	fun sharedPreferences(): SPreferences
	fun intentReceiveServiceControl(): ((Boolean) -> Unit) -> Intent
	fun snoreServiceControl(): ((Boolean) -> Unit) -> Unit
	fun bindIntentReceiveService(): Flow<IntentBroadcastReceiverService>
	fun bindSnoreService(): Flow<MicrophoneService>
	fun notificationProvider(): NotificationProvider
	fun microphone(): Microphone
	fun permissions(): Permissions
	fun mediaPlayer(): (Int) -> MediaPlayer
}