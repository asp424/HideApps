package com.lm.hideapps.notification

import android.app.Notification
import android.app.Notification.CATEGORY_SERVICE
import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager.*
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import javax.inject.Inject

interface ServiceNotification {
	
	fun serviceNotification(): Notification
	
	class Base @Inject constructor(
		private val notificationManager: NotificationManagerCompat,
		private val notificationBuilder: Builder,
		private val resources: Resources
	) : ServiceNotification {
		override fun serviceNotification(): Notification {
			serviceChannel
			return notificationBuilder
				.setOngoing(true)
				.setContentTitle(resources.getString(R.string.name))
				.setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
				.setPriority(PRIORITY_MIN)
				.setCategory(CATEGORY_SERVICE)
				.build()
		}
		
		private val serviceChannel by lazy {
			resources.getString(R.string.name).also { title ->
				NotificationChannel(title, title, IMPORTANCE_DEFAULT).apply {
					lockscreenVisibility = VISIBILITY_PRIVATE
					setSound(null, null)
					notificationManager.createNotificationChannel(this)

				}
			}
		}
	}
}