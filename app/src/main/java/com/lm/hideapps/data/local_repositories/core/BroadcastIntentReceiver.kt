package com.lm.hideapps.data.local_repositories.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastIntentReceiver(private val onReceive: (String) -> Unit)
	: BroadcastReceiver() {
	
	override fun onReceive(context: Context?, intent: Intent?) =
		onReceive(intent?.action.toString())
}