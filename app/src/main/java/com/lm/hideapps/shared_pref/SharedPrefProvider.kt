package com.lm.hideapps.shared_pref

import android.content.SharedPreferences
import javax.inject.Inject


interface SharedPrefProvider {
	
	fun run(): Boolean
	
	fun stop(): Boolean
	
	fun isRunning(): Boolean
	
	class Base @Inject constructor(
		private val sharedPreferences: SharedPreferences,
	) :
		SharedPrefProvider {
		
		override fun run() = true.apply { sharedPreferences.edit().putBoolean("0", this).apply() }
		
		override fun stop() = false.apply { sharedPreferences.edit().putBoolean("0", this).apply() }
		
		override fun isRunning() = sharedPreferences.getBoolean("0", false)
	}
}