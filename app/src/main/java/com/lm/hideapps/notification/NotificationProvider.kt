package com.lm.hideapps.notification

import android.app.Notification
import kotlinx.coroutines.Job
import javax.inject.Inject


interface NotificationProvider {
	
	fun serviceNotification(): Notification
	
	fun actionNotification(action: String): Job
	
	class Base @Inject constructor(
		private val serviceNotification: ServiceNotification,
		private val actionsNotification: ActionsNotification
	) : NotificationProvider {

		override fun serviceNotification()
		= serviceNotification.serviceNotification()
		
		override fun actionNotification(action: String)
		= actionsNotification.actionNotification(action)
	}
}