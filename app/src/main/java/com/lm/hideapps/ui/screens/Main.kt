package com.lm.hideapps.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ServiceControl() {
	appComponent.apply {
		var isRunningText by remember {
			mutableStateOf(if (sharedPreferences().isRunning()) "running" else "stopped")
		}
		var isRunning by remember { mutableStateOf(sharedPreferences().isRunning()) }
		var action by remember { mutableStateOf("") }
		var fetchActions by remember { mutableStateOf(false) }
		var activesJob: Job by remember { mutableStateOf(Job().apply { cancel() }) }
		val coroutine = rememberCoroutineScope()
		LaunchedEffect(fetchActions) {
			if (fetchActions) {
				if (!activesJob.isActive) {
					activesJob = coroutine.launch {
						bindService().collect {
							it.receiver(actions).collect { act -> action = act }
						}
					}
				}
			} else {
				activesJob.cancel(); action = ""
			}
		}
		Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
			Button(
				{ isRunning = !isRunning
					serviceControl().invoke { isRunningText = if (it) "running" else "stopped" }
				},
				Modifier.padding(bottom = 10.dp),
				enabled = !(isRunning && fetchActions && sharedPreferences().isRunning())
			) { Text(text = isRunningText) }
			
			Button(
				{ fetchActions = !fetchActions },
				Modifier.padding(bottom = 10.dp)
			) { Text(text = if (fetchActions) "connected" else "disconnected") }
			Text(text = action)
		}
	}
}

private val actions by lazy {
	listOf(
		Intent.ACTION_POWER_CONNECTED,
		Intent.ACTION_HEADSET_PLUG,
		Intent.ACTION_POWER_DISCONNECTED
	)
}
