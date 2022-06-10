package com.lm.hideapps.core

import android.app.Application
import android.content.Context
import com.lm.hideapps.di.dagger.AppComponent
import com.lm.hideapps.di.dagger.DaggerAppComponent

class App : Application() {
	val appComponent by lazy { DaggerAppComponent.builder().context(this).create() }
}






