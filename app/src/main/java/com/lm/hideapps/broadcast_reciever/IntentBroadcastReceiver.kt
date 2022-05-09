package com.lm.hideapps.broadcast_reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class IntentBroadcastReceiver(private val onReceive: (String) -> Unit)
	: BroadcastReceiver() {
	
	override fun onReceive(context: Context?, intent: Intent?) =
		onReceive(intent?.action.toString())
}