package com.lm.hideapps.core

import android.util.Log

object MyLog {

    val <T> T.log get() = Log.d("My", toString())
}