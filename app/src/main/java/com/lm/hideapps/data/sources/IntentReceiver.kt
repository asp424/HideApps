package com.lm.hideapps.data.sources

import android.content.BroadcastReceiver

interface IntentReceiver {
	
	fun broadcastReceiver(onReceive: (String) -> Unit):BroadcastReceiver
}