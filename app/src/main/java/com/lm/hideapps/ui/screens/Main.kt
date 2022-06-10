package com.lm.hideapps.ui.screens

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ServiceControl() {
    var volumeJob: Job by remember { mutableStateOf(Job().apply { cancel() }) }
    appComponent.apply {
        var isRunning by remember { mutableStateOf(sharedPreferences().isRunning()) }
        val coroutine = rememberCoroutineScope()
        var volume by rememberSaveable { mutableStateOf("0") }
        var fetchVolume by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(fetchVolume) {
            if (fetchVolume) {
                if (!volumeJob.isActive) { volumeJob = coroutine.launch {
                        bindSnoreService().collect {
                            it.micLevelFlow.collect { vol -> volume = vol.toString() }
                        }
                    }
                }
            } else volumeJob.cancel(); volume = "0"
        }

        Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
            Button(
                { snoreServiceControl().invoke { isRunning = it } },
                Modifier.padding(bottom = 10.dp),
                enabled = !(isRunning && fetchVolume && sharedPreferences().isRunning())
            ) { Text(text = if (isRunning) "stop listen snore" else "start listen snore") }

            Button(
                { fetchVolume = !fetchVolume },
                Modifier.padding(bottom = 10.dp), enabled = isRunning
            ) { Text(text = if (fetchVolume) "stop getting mic level" else "get mic level") }

            Text(volume)
        }
    }
}






