package com.lm.hideapps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lm.hideapps.MainActivity
import com.lm.hideapps.shared_pref.SharedPrefProvider
import com.lm.hideapps.ui.vm.IntentViewModel

@Composable
fun Main(intentViewModel: IntentViewModel, sharedPreferences: SharedPrefProvider) {
	var isRunningText by remember { mutableStateOf(sharedPreferences.isRunning().toString()) }
		Column(
			Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Button(
				onClick = { intentViewModel.service.invoke { isRunningText = it.toString() } },
				modifier = Modifier.padding(bottom = 10.dp)
			) { Text(text = isRunningText) }
		}
}