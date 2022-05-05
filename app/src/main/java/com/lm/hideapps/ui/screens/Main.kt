package com.lm.hideapps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lm.hideapps.MainActivity
import com.lm.hideapps.ui.vm.IntentViewModel

@Composable
fun Main(intentViewModel: IntentViewModel) {
	//val text by intentViewModel.intentAction.collectAsState()
	(LocalContext.current as MainActivity).apply {
		Column(
			Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			//Text(text = text, modifier = Modifier.padding(top = 10.dp))
			
			Button(
				onClick = { intentViewModel.observeIntentAction()
						  },
				modifier = Modifier.padding(bottom = 10.dp)
			) {
				
				Text(text = "start")
				
			}
			
			Button(
				onClick = { intentViewModel.stopObserveIntentAction()
				}
			) {
				Text(text = "stop")
			}
		}
	}
}