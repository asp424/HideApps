package com.lm.hideapps.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import javax.inject.Inject

interface ServiceNotificationInteractor {
	
	fun serviceNotification(): Notification
	
	class Base @Inject constructor(
		private val notificationManager: NotificationManagerCompat,
		private val notificationBuilder: NotificationCompat.Builder,
		private val activityIntent: PendingIntent
	) : ServiceNotificationInteractor {
		override fun serviceNotification(): Notification {
			serviceChannel
			return notificationBuilder
				.setOngoing(true)
				.setContentTitle(startService)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setCategory(NotificationCompat.CATEGORY_SERVICE)
				.setContentIntent(activityIntent)
				.build()
		}
		
		private val serviceChannel by lazy {
			NotificationChannel(startService, startService, IMPORTANCE_HIGH).apply {
				lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
				notificationManager.createNotificationChannel(this)
			}
		}
		
		private val startService by lazy { "StartIntentService" }
		
	}
}