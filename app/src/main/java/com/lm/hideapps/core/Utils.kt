package com.lm.hideapps.core

import android.util.Log

val <T> T.log get() = Log.d("My", toString())
