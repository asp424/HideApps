package com.lm.hideapps.core

import android.content.SharedPreferences
import javax.inject.Inject


interface SPreferences {

    fun run(): Boolean

    fun saveLevel(level: Float)

    fun readLevel(): Float

    fun stop(): Boolean

    fun isRunning(): Boolean

    class Base @Inject constructor(
        private val sharedPreferences: SharedPreferences,
    ) :
        SPreferences {

        override fun run() = true.apply { sharedPreferences.edit().putBoolean("0", this).apply() }

        override fun saveLevel(level: Float) = sharedPreferences.edit().putFloat("1", level).apply()

        override fun readLevel() = sharedPreferences.getFloat("1", 0.5f)

        override fun stop() = false.apply { sharedPreferences.edit().putBoolean("0", this).apply() }

        override fun isRunning() = sharedPreferences.getBoolean("0", false)
    }
}