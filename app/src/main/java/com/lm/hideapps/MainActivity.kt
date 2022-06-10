package com.lm.hideapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lm.hideapps.core.appComponent
import com.lm.hideapps.di.compose_di.MainDependencies
import com.lm.hideapps.ui.screens.ServiceControl

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.permissions().apply {
            launchIfHasPermissions {
                setContent {
                    MainDependencies(appComponent) {
                        ServiceControl()
                    }
                }
            }
        }
    }
}
