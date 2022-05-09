package com.lm.hideapps.notification

import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import kotlinx.coroutines.*
import javax.inject.Inject

interface ActionsNotification {
	
	fun actionNotification(action: String): Job
	
	fun hideNotification(id: Int)
	
	fun actionNotification(title: String, importance: Int, id: Int)
	
	class Base @Inject constructor(
		private val notificationBuilder: NotificationCompat.Builder,
		private val notificationManager: NotificationManagerCompat,
		private val resources: Resources
	) : ActionsNotification {
		
		override fun actionNotification(
			title: String,
			importance: Int,
			id: Int
		) {
			actionChannel(title, title, importance)
			notificationManager.notify(
				id, notificationBuilder
					.setOngoing(true)
					.setContentTitle(title)
					.setSmallIcon(R.mipmap.ic_launcher)
					.setPriority(PRIORITY_MAX)
					.build()
			)
		}
		
		private val actionChannel: (String, String, Int) -> Unit by lazy {
			{ channel, name, importance ->
				notificationManager
					.createNotificationChannel(NotificationChannel(channel, name, importance))
			}
		}
		
		override fun actionNotification(action: String) =
			scope.launch {
				actionNotification(action.cutAction, IMPORTANCE_HIGH, 1)
				delayShow.invoke()
				hideNotification(1)
			}
		
		override fun hideNotification(id: Int) = notificationManager.cancel(id)
		
		private val scope by lazy { CoroutineScope(Dispatchers.IO) }
		private val delayShow by lazy { suspend { delay(2000L) } }
		private val String.cutAction get() = substringAfter(resources.getString(R.string.substring))
	}
}