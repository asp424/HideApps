package com.lm.hideapps.data.local_repositories

import android.content.Context
import android.content.IntentFilter
import com.lm.hideapps.data.local_repositories.core.BroadcastIntentReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class BroadcastIntentRepository  {

    private suspend fun receiveBroadcastIntentAsFlow(actions: List<String>, context: Context)
    = callbackFlow {
        BroadcastIntentReceiver { trySendBlocking(it) }.also { receiver ->
            actions.forEach { context.registerReceiver(receiver, IntentFilter(it)) }
            awaitClose { context.unregisterReceiver(receiver) }
        }
    }.flowOn(Dispatchers.IO)
}