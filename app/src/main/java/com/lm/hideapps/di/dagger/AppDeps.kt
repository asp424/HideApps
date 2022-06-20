package com.lm.hideapps.di.dagger

import android.content.Context
import android.content.res.Resources
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.core.ResourcesManager
import com.lm.hideapps.core.SPreferences

interface AppDeps {
    val resourcesManager: ResourcesManager
}