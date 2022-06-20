package com.lm.hideapps.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lm.hideapps.R
import com.lm.hideapps.di.dagger.AppComponent
import com.lm.hideapps.di.dagger.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(this)
            .resourcesManager(ResourcesManager.Base(resources,packageName))
            .sharedPreferences(SPreferences.Base(
                getSharedPreferences(getString(R.string.sPreferencesName), MODE_PRIVATE)
            ))
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> (applicationContext as App).appComponent
    }






