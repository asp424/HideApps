package com.lm.hideapps.notification

import android.app.Notification
import android.app.Notification.CATEGORY_SERVICE
import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import javax.inject.Inject

interface ServiceNotification {
	
	fun serviceNotification(): Notification
	
	class Base @Inject constructor(
		private val notificationManager: NotificationManagerCompat,
		private val notificationBuilder: NotificationCompat.Builder,
		private val resources: Resources
	) : ServiceNotification {
		override fun serviceNotification(): Notification {
			serviceChannel
			return notificationBuilder
				.setOngoing(true)
				.setContentTitle(resources.getString(R.string.name))
				.setSmallIcon(R.mipmap.ic_launcher)
				.setPriority(PRIORITY_MAX)
				.setCategory(CATEGORY_SERVICE)
				.build()
		}
		
		private val serviceChannel by lazy {
			resources.getString(R.string.name).also { title ->
				NotificationChannel(title, title, IMPORTANCE_HIGH).apply {
					lockscreenVisibility = VISIBILITY_PRIVATE
					notificationManager.createNotificationChannel(this)
				}
			}
		}
	}
}