package com.lm.hideapps.notification

import android.app.Notification
import kotlinx.coroutines.Job
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
		= actionsNotificationInteractor.actionNotification(action)
	
	}
}