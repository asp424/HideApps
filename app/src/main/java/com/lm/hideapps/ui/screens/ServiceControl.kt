package com.lm.hideapps.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lm.expandedcolumn.ExpandedItem
import com.lm.hideapps.R
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ServiceControl() {
	var activesJob: Job by remember { mutableStateOf(Job().apply { cancel() }) }
	appComponent.apply {
		var isRunning by remember { mutableStateOf(sharedPreferences().isRunning()) }
		val coroutine = rememberCoroutineScope()
		var swipeText by remember { mutableStateOf(false) }
		val width = LocalConfiguration.current.screenWidthDp.dp
		var action by rememberSaveable { mutableStateOf("") }
		var fetchActions by rememberSaveable { mutableStateOf(false) }
		LaunchedEffect(fetchActions) {
			if (fetchActions) {
				if (!activesJob.isActive) {
					activesJob = coroutine.launch {
						bindService().collect {
							Log.d("My", it.toString())
							it.actionFlow.collect { act ->
								swipeText = true
								delay(200)
								action = act
								swipeText = false
							}
						}
					}
				}
			} else {
				activesJob.cancel(); action = ""
			}
		}
		Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
			Button(
				{ serviceControl().invoke { isRunning = it } },
				Modifier.padding(bottom = 10.dp),
				enabled = !(isRunning && fetchActions && sharedPreferences().isRunning())
			) { Text(text = if (isRunning) "running" else "stopped") }
			
			Button(
				{ fetchActions = !fetchActions },
				Modifier.padding(bottom = 10.dp), enabled = isRunning
			) { Text(text = if (fetchActions) "connected" else "disconnected") }
			
			Text(
				text = action.substringAfter(stringResource(R.string.substring)),
				modifier = Modifier.offset(
					animateDpAsState(if (swipeText) width else 0.dp).value, 0.dp
				)
			)
		}
	}
}

val listItems
	get() = listOf(
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		ExpandedItem("Hi", Icons.Sharp.Face, Black, White, White, onClick = { Log.d("My", "test") }),
		)






