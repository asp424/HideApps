package com.lm.hideapps.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase.Base.Companion.SCALE
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase.Base.Companion.START_LOADING
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase.Base.Companion.STOP_LOADING
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun Main() {
    var tempJob: Job by remember { mutableStateOf(Job()) }
    appComponent.apply {
        var isRunning by remember { mutableStateOf(sPreferences().isRunning()) }
        val coroutine = rememberCoroutineScope()
        var temp by rememberSaveable { mutableStateOf("") }
        var text by rememberSaveable { mutableStateOf("") }
        var fetchTemp by rememberSaveable { mutableStateOf(false) }
        var isLoading by rememberSaveable { mutableStateOf("null") }
        var isRecognize by remember { mutableStateOf("true") }
        var starter by remember { mutableStateOf(0) }
        var sliderPosition by remember { mutableStateOf(sPreferences().readLevel()) }

        LaunchedEffect(starter) {
            if (isRecognize.toBoolean() && starter != 0) {
                isRecognize = "false"
                microphoneRepository().recognizedWordsAsFlow("ru-RU").collect {
                    if (it[0] == "true") isRecognize = it[0]
                    else text = it.toString()
                        .substringAfter("[").substringBefore("]")
                }
            }
        }

        LaunchedEffect(fetchTemp) {
            if (fetchTemp) {
                if (!tempJob.isActive) {
                    tempJob = coroutine.launch {
                        microphoneServiceConnection().collect {
                            it.temperatureForUI.collect { t ->
                                isLoading =
                                    if (t == START_LOADING.toString())
                                    START_LOADING.toString() else STOP_LOADING.toString()
                                temp = t
                            }
                        }
                    }
                }
            } else tempJob.cancel(); temp = ""
        }

        LaunchedEffect(sliderPosition) {
            sPreferences().saveLevel(sliderPosition)
        }

        LaunchedEffect(isRunning){
        isRecognize = (!isRunning).toString()
        }

        Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
            Button(
                { microphoneServiceControl().invoke { isRunning = it } },
                Modifier.padding(bottom = 10.dp),
                enabled = !(isRunning && fetchTemp && sPreferences().isRunning())
            ) { Text(text = if (isRunning) "stop listen mic" else "start listen mic") }

            if (!isRunning) {
                Slider(value = sliderPosition, onValueChange = {
                    sliderPosition = it
                }, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }

            Text(
                text = (sliderPosition * SCALE).toInt().toString(), fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic

            )
            Text(text = "mic trigger level")

            Button(
                { fetchTemp = !fetchTemp },
                Modifier.padding(bottom = 10.dp, top = 10.dp), enabled = isRunning
            ) { Text(text = if (fetchTemp) "stop binding" else "bind to getting gis temp") }


            Button(
                { starter++ },
                Modifier.padding(bottom = 10.dp), enabled = isRecognize.toBoolean()
            ) { Text(text = "recognize speech") }
            Text(text = text)
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





