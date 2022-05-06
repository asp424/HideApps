package com.lm.hideapps.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.res.Resources
import android.graphics.Color.BLUE
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import javax.inject.Inject


interface NotificationProvider {
	
	fun notification(text: String, outgoing: Boolean): Notification
	
	fun showNotification(text: String, id: Int, outgoing: Boolean)
	
	fun hideNotification()
	
	class Base @Inject constructor(
		private val resources: Resources,
		private val notificationBuilder: Builder,
		private val notificationManager: NotificationManagerCompat
	) : NotificationProvider {
		
		override fun notification(text: String, outgoing: Boolean): Notification {
			createChannel
			return notificationBuilder
				.setOngoing(outgoing)
				.setContentTitle(text)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setPriority(PRIORITY_MAX)
				.setCategory(CATEGORY_SERVICE)
				.build()
		}
		
		private val createChannel by lazy {
			NotificationChannel(
				resources.getString(R.string.channel),
				resources.getString(R.string.name),
				IMPORTANCE_HIGH
			).apply {
				lightColor = BLUE
				lockscreenVisibility = VISIBILITY_PRIVATE
				notificationManager.createNotificationChannel(this)
			}
		}
		
		override fun showNotification(text: String, id: Int, outgoing: Boolean)
		= notificationManager.notify(id, notification(text, outgoing))
		
		override fun hideNotification() = notificationManager.cancelAll()
	}
}