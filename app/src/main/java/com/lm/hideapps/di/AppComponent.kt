package com.lm.hideapps.di

import android.app.Application
import android.app.NotificationManager
import android.content.ComponentName
import android.content.ServiceConnection
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.MainActivity
import com.lm.hideapps.notification.NotificationProvider
import com.lm.hideapps.receiver_service.IntentReceiveService
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
		fun notificationBuilder(notificationManager: NotificationCompat.Builder): Builder
		
		@BindsInstance
		fun notificationManager(notificationBuilder: NotificationManagerCompat): Builder
		
		@BindsInstance
		fun controlIntentService(flow: Flow<IntentReceiveService>): Builder
		
		@BindsInstance
		fun resources(resources: Resources): Builder
		
		fun create(): AppComponent
	}
	
	fun inject(intentReceiveService: IntentReceiveService)
	
	fun inject(mainActivity: MainActivity)
	
}