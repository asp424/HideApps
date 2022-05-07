package com.lm.hideapps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.lm.hideapps.core.App
import com.lm.hideapps.receiver_service.IntentReceiveService
import com.lm.hideapps.shared_pref.SharedPrefProvider
import com.lm.hideapps.ui.screens.Main
import com.lm.hideapps.ui.vm.IntentViewModel
import com.lm.hideapps.ui.vm.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {
	
	private var intentA: Intent? = null
	
	@Inject
	lateinit var vmFactory: ViewModelFactory
	
	@Inject
	lateinit var sharedPreferences: SharedPrefProvider
	
	private val intentViewModel: IntentViewModel by viewModels { vmFactory }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		intentA = Intent(applicationContext, IntentReceiveService::class.java)
		(applicationContext as App).appComponent.inject(this)
		setContent { Main(intentViewModel, sharedPreferences) }
		
	}
}

