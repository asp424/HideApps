package com.lm.hideapps.core

import android.content.res.Resources
import com.lm.hideapps.R
import javax.inject.Inject

interface ResourcesManager {

    fun string(stringResource: Int): String

    fun raw(value: String): Int

    class Base @Inject constructor(
        private val resources: Resources,
        private val packageName: String
    ) : ResourcesManager {

        override fun string(stringResource: Int) = resources.getString(stringResource)

        override fun raw(value: String) = resources.getIdentifier(
            value, string(R.string.resource_type_raw), packageName
        )
    }
}