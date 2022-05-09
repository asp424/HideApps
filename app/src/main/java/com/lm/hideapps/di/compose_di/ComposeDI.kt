package com.lm.hideapps.di.compose_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.lm.hideapps.di.dagger.AppComponent

@Composable
fun MainDependencies(appComponent: AppComponent, content: @Composable () -> Unit) =
	CompositionLocalProvider(Local provides appComponent, content = content)

private val Local = staticCompositionLocalOf<AppComponent> { error("No value provided") }

object MainDep { val appComponent @Composable get() = Local.current }





