package com.lm.hideapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.lm.hideapps.core.App
import com.lm.hideapps.shared_pref.SharedPrefProvider
import com.lm.hideapps.ui.screens.Main
import com.lm.hideapps.ui.vm.IntentViewModel
import com.lm.hideapps.ui.vm.ViewModelFactory
import javax.inject.Inject


class MainActivity : ComponentActivity() {
	
	@Inject
	lateinit var vmFactory: ViewModelFactory
	
	@Inject
	lateinit var sharedPreferences: SharedPrefProvider
	
	private val intentViewModel: IntentViewModel by viewModels { vmFactory }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		(applicationContext as App).appComponent.inject(this)
		setContent { Main(intentViewModel, sharedPreferences) }
	}
}