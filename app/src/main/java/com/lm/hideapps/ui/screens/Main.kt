package com.lm.hideapps.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun Main() {
    var tempJob: Job by remember { mutableStateOf(Job()) }
    appComponent.apply {
        var isRunning by remember { mutableStateOf(sharedPreferences().isRunning()) }
        val coroutine = rememberCoroutineScope()
        var temp by rememberSaveable { mutableStateOf("") }
        var fetchTemp by rememberSaveable { mutableStateOf(false) }
        var isLoading by rememberSaveable { mutableStateOf("null") }

        LaunchedEffect(fetchTemp) {
            if (fetchTemp) {
                if (!tempJob.isActive) {
                    tempJob = coroutine.launch {
                        bindSnoreService().collect {
                            it.temp.collect { t ->
                                isLoading = if (t == "true") t else "false"
                                temp = t
                            }
                        }
                    }
                }
            } else tempJob.cancel(); temp = ""
        }

        Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
            Button(
                { snoreServiceControl().invoke { isRunning = it } },
                Modifier.padding(bottom = 10.dp),
                enabled = !(isRunning && fetchTemp && sharedPreferences().isRunning())
            ) { Text(text = if (isRunning) "stop listen mic" else "start listen mic") }

            Button(
                { fetchTemp = !fetchTemp },
                Modifier.padding(bottom = 10.dp), enabled = isRunning
            ) { Text(text = if (fetchTemp) "stop getting gis temp" else "get gis temp") }

            Box(modifier = Modifier.padding(top = 60.dp)) {
                if (!isLoading.toBoolean()) {
                    Text(temp, fontSize = 60.sp)
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(60.dp))
                }
            }
        }
    }
}






