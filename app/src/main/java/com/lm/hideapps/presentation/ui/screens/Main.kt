package com.lm.hideapps.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.lm.hideapps.di.compose_di.MainDep.appComponent
import com.lm.hideapps.presentation.view_models.MainViewModel
import com.lm.hideapps.service.LocalServiceBindState
import com.lm.hideapps.service.LocalServiceState
import com.lm.hideapps.use_cases.MicrophoneServiceUseCase.Base.Companion.SCALE

@Composable
fun Main(mainViewModel: MainViewModel) {
    appComponent.apply {
        val temperature by mainViewModel.temperature.collectAsState()
        val serviceIsRunning by mainViewModel.serviceIsRunning.collectAsState()
        val isBindToService by mainViewModel.isBindToService.collectAsState()
        var sliderPosition by remember { mutableStateOf(sPreferences().readMicrophoneLevel()) }

        LaunchedEffect(sliderPosition) {
            sPreferences().saveMicrophoneLevel(sliderPosition)
        }

        Column(Modifier.fillMaxSize(), Center, CenterHorizontally) {
            Button(
                {
                    if (serviceIsRunning == LocalServiceState.STOPPED)
                        mainViewModel.startMicrophoneService()
                    else mainViewModel.stopMicrophoneService()
                },
                Modifier.padding(bottom = 10.dp),
                enabled = isBindToService == LocalServiceBindState.UNBINDING

            ) {
                Text(
                    text = if (serviceIsRunning == LocalServiceState.RUNNING)
                        "stop listen mic" else "start listen mic"
                )
            }

            if (serviceIsRunning == LocalServiceState.STOPPED) {
                Slider(value = sliderPosition, onValueChange = {
                    sliderPosition = it
                }, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }

            Text(
                text = (sliderPosition * SCALE).toInt().toString(),
                fontWeight = Bold, fontStyle = Italic
            )

            Text(text = "mic trigger level")

            Button(
                {
                    if (isBindToService == LocalServiceBindState.UNBINDING)
                        mainViewModel.bindToMicrophoneService()
                    else mainViewModel.unBindToMicrophoneService()
                },
                Modifier.padding(bottom = 10.dp, top = 10.dp),
                enabled = serviceIsRunning == LocalServiceState.RUNNING

            ) {
                Text(
                    text = if (isBindToService == LocalServiceBindState.BINDING)
                        "stop binding" else "bind to getting gis temp"
                )
            }

            Box(modifier = Modifier.padding(top = 60.dp)) {
                when (temperature) {
                    is TemperatureStates.OnSuccess -> Text(
                        (temperature as TemperatureStates.OnSuccess).temperature
                    )
                    is TemperatureStates.OnLoading ->
                        CircularProgressIndicator(modifier = Modifier.size(60.dp))

                    is TemperatureStates.OnInit -> Text("weather here")
                }
            }
        }
    }
}






