package com.lm.hideapps.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.core.appComponent
import com.lm.hideapps.di.compose_di.MainDependencies
import com.lm.hideapps.presentation.ui.screens.Main
import com.lm.hideapps.presentation.view_models.MainViewModel
import com.lm.hideapps.presentation.view_models.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var permissions: Permissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        val mainViewModel by viewModels<MainViewModel> { viewModelFactory }
        permissions.apply {
            launchIfHasPermissions {
                setContent {
                    MainDependencies(appComponent) { Main(mainViewModel) }
                }
            }
        }
    }
}
