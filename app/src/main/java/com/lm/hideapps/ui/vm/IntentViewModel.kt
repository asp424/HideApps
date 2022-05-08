package com.lm.hideapps.ui.vm

import androidx.lifecycle.ViewModel
import com.lm.hideapps.shared_pref.SharedPrefProvider
import javax.inject.Inject

class IntentViewModel @Inject constructor(
	private val serviceControl: ((Boolean) -> Unit) -> Unit,
	private val bindService: () -> Any
) : ViewModel() {
	
	val service = serviceControl
	
	val bind = bindService
}


