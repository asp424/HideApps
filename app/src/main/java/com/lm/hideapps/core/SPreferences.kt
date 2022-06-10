package com.lm.hideapps.core

import android.content.SharedPreferences
import javax.inject.Inject


interface SPreferences {
	
	fun run(): Boolean
	
	fun stop(): Boolean
	
	fun isRunning(): Boolean
	
	class Base @Inject constructor(
		private val sharedPreferences: SharedPreferences,
	) :
        SPreferences {
		
		override fun run() = true.apply { sharedPreferences.edit().putBoolean("0", this).apply() }
		
		override fun stop() = false.apply { sharedPreferences.edit().putBoolean("0", this).apply() }
		
		override fun isRunning() = sharedPreferences.getBoolean("0", false)
	}
}