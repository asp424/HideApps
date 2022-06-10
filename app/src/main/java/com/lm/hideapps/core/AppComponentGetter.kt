package com.lm.hideapps.core

import android.content.Context
import com.lm.hideapps.di.dagger.AppComponent

object AppComponentGetter {
    val Context.appComponent: AppComponent
        get() = when (this) { is App -> appComponent else -> (applicationContext as App).appComponent }

}