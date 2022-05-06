package com.lm.hideapps.data.sources

import android.content.BroadcastReceiver

interface IntentReceiver {
	
	val broadcastReceiver: ((String) -> Unit) -> BroadcastReceiver
}