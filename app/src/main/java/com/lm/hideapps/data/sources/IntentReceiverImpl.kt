package com.lm.hideapps.data.sources

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class IntentReceiverImpl @Inject constructor() : IntentReceiver {
	
	override val broadcastReceiver: ((String) -> Unit) -> BroadcastReceiver
		get() =
			{ onReceive ->
				object : BroadcastReceiver() {
					override fun onReceive(context: Context?, intent: Intent?) {
						onReceive(intent?.action.toString())
					}
				}
			}
}
