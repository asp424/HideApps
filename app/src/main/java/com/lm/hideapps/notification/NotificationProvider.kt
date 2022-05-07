package com.lm.hideapps.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import com.lm.hideapps.receiver_service.IntentReceiveService.Companion.listActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


interface NotificationProvider {
	
	fun serviceNotification(): Notification
	
	fun actionNotification(action: String): Job
	
	class Base @Inject constructor(
		private val serviceNotificationInteractor: ServiceNotificationInteractor,
		private val actionsNotificationInteractor: ActionsNotificationInteractor
	) : NotificationProvider {
		override fun serviceNotification() = serviceNotificationInteractor.serviceNotification()
		
		override fun actionNotification(action: String): Job
		= actionsNotificationInteractor.showActionNotification(action)
	
	}
}