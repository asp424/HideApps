package com.lm.hideapps.ui.vm

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntentViewModel @Inject constructor(
	private val startIntentService: (((String) -> Flow<String>) -> Unit) -> Unit
) : ViewModel() {
	
	var job: Job = Job()
	
	fun observeIntentAction() = startIntentService { receiver ->
		if (!job.isActive) 
		job = viewModelScope.launch {
			receiver(Intent.ACTION_HEADSET_PLUG).collect {
				Log.d("My", it)
			}
		}
	}
	
	fun stopObserveIntentAction() = job.cancel()
}