package com.lm.hideapps.core

import android.Manifest.permission.RECORD_AUDIO
import android.app.ActivityManager
import android.app.Application
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.lm.hideapps.di.dagger.AppDeps
import com.lm.hideapps.presentation.MainActivity
import javax.inject.Inject

interface Permissions {

    fun MainActivity.launchIfHasPermissions(unit: () -> Unit)

    fun checkSinglePermission(permission: String): Boolean

    class Base @Inject constructor(private val context: Application) : Permissions {

        private val checkMultiPermissions by lazy {
            listOfPerm.all {
                ActivityCompat.checkSelfPermission(context, it) == PERMISSION_GRANTED
            }
        }

        override fun checkSinglePermission(permission: String) =
            context.checkSelfPermission(permission) == PERMISSION_GRANTED

        private fun MainActivity.permissionsLauncherRegistration(onAllPermissionsGet: () -> Unit) =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { result ->
                if (result.entries.all { it.value }) onAllPermissionsGet()
                else { clearUserData; finish() }
            }

        override fun MainActivity.launchIfHasPermissions(unit:() -> Unit) {
               if (checkMultiPermissions) unit() else permissionsLauncherRegistration { unit() }
                   .launch(listOfPerm)
           }

        private val MainActivity.clearUserData
            get() =
                (getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()

                private val listOfPerm = arrayOf(RECORD_AUDIO)
    }
}




