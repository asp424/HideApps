package com.lm.hideapps.di.compose_di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.lm.hideapps.di.dagger.AppComponent

private val LocalMainDependencies = staticCompositionLocalOf<AppComponent> { error("No value provided") }

@Composable
fun MainDependencies(appComponent: AppComponent, content: @Composable () -> Unit) {
	
	CompositionLocalProvider(
		LocalMainDependencies provides appComponent, content = content
	)
}

object MainDep { val appComponent: AppComponent @Composable get() = LocalMainDependencies.current }





