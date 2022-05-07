package com.lm.hideapps.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import com.lm.hideapps.receiver_service.IntentReceiveService
import kotlinx.coroutines.*
import javax.inject.Inject


interface ActionsNotificationInteractor {
	
	fun showNotification(
		title: String,
		channel: String,
		name: String,
		importance: Int,
		id: Int
	)
	
	fun showActionNotification(action: String): Job
	
	fun hideNotification(id: Int)
	
	fun actionNotification(
		title: String,
		channel: String,
		name: String,
		importance: Int
	): Notification
	
	class Base @Inject constructor(
		private val notificationBuilder: NotificationCompat.Builder,
		private val notificationManager: NotificationManagerCompat,
		private val activityIntent: PendingIntent
	) : ActionsNotificationInteractor {
		
		override fun actionNotification(
			title: String,
			channel: String,
			name: String,
			importance: Int
		): Notification {
			actionChannel(channel, name, importance)
			return notificationBuilder
				.setOngoing(true)
				.setContentTitle(title)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setCategory(NotificationCompat.CATEGORY_SERVICE)
				.setContentIntent(activityIntent)
				.build()
		}
		
		private val actionChannel: (String, String, Int) -> Unit by lazy {
			{ channel, name, importance ->
				NotificationChannel(channel, name, importance).apply {
					lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
					notificationManager.createNotificationChannel(this)
				}
			}
		}
		
		override fun showActionNotification(action: String) =
			with(IntentReceiveService.listActions) {
				when (action) {
					get(0) ->
						scope.launch {
							showNotification(connected, connected, connected,
								NotificationManager.IMPORTANCE_HIGH, 1
							)
							delayShow.invoke()
							hideNotification(1)
						}
					get(1) ->
						scope.launch {
							showNotification(plugged, plugged, plugged,
								NotificationManager.IMPORTANCE_HIGH, 2
							)
							delayShow.invoke()
							hideNotification(2)
						}
					get(2) ->
						scope.launch {
							showNotification(disconnected, disconnected, disconnected,
								NotificationManager.IMPORTANCE_HIGH, 3
							)
							delayShow.invoke()
							hideNotification(3)
						}
					else -> Job()
				}
			}
		
		override fun hideNotification(id: Int) = notificationManager.cancel(id)
		
		override fun showNotification(
			title: String,
			channel: String,
			name: String,
			importance: Int,
			id: Int
		) = notificationManager.notify(id, actionNotification(title, channel, name, importance))
		
		private val connected by lazy { "Charger connected" }
		private val plugged by lazy { "Headset plugged" }
		private val disconnected by lazy { "Charger disconnected" }
		private val scope by lazy { CoroutineScope(Dispatchers.IO) }
		private val delayShow by lazy { suspend { delay(2000L) } }
	}
}