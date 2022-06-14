package com.lm.hideapps.data.local_repositories

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface BroadcastIntentRepository {

    fun receiveBroadcastIntentsAsFlow(actions: List<String>, context: Context): Flow<String>

    fun broadcastReceiver(onReceive: (String) -> Unit): BroadcastReceiver

    infix fun String.editIntentAction(action: String): String

    class Base : BroadcastIntentRepository {

        override fun receiveBroadcastIntentsAsFlow(actions: List<String>, context: Context) =
            callbackFlow {
                broadcastReceiver { trySendBlocking(it) }
                    .also { receiver ->
                        actions.forEach {
                            context.registerReceiver(receiver, IntentFilter(it))
                        }
                        awaitClose { context.unregisterReceiver(receiver) }
                    }
            }

        override fun broadcastReceiver(onReceive: (String) -> Unit) =
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    onReceive(
                        if (intent == null) NULL_ACTION
                        else
                            BASE_PART_OF_ACTION_NAME editIntentAction intent.action.toString()
                    )
                }
            }

        override infix fun String.editIntentAction(action: String) =
            action.substringAfter(this)

        companion object {
            const val NULL_ACTION = "null"
            const val BASE_PART_OF_ACTION_NAME = "android.intent.action."
        }
    }
}