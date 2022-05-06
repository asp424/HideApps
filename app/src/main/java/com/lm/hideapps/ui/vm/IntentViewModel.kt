package com.lm.hideapps.ui.vm

import android.content.Intent
import android.content.Intent.*
import androidx.lifecycle.ViewModel
import com.lm.hideapps.notification.NotificationProvider
import com.lm.hideapps.receiver_service.IntentReceiveService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntentViewModel @Inject constructor(
	private val controlIntentServer: Flow<IntentReceiveService>,
	private val notification: NotificationProvider
) : ViewModel() {
	
	fun observeIntentAction() {
		IntentReceiveService().apply {
			scope.launch {
				
				controlIntentServer.collect {
					it.receiver(
						listOf(
							ACTION_POWER_CONNECTED,
							ACTION_HEADSET_PLUG,
							ACTION_POWER_DISCONNECTED
						)
					).collect { action ->
						when(action){
							ACTION_POWER_CONNECTED ->
								notification.showNotification("Charger connected", 1, false)
							ACTION_HEADSET_PLUG ->
								notification.showNotification("Headset plugged action", 2, false)
							ACTION_POWER_DISCONNECTED ->
								notification.showNotification("Charger disconnected", 3, false)
						}
					}
				}
			}
		}
	}
	
	fun stopObserveActionIntent() = IntentReceiveService().scope.cancel()
}


