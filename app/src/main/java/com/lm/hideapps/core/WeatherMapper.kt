package com.lm.hideapps.core

import android.content.res.Resources
import androidx.core.text.isDigitsOnly
import com.lm.hideapps.R
import com.lm.hideapps.data.remote_repositories.LoadStates
import javax.inject.Inject
import kotlin.math.round

interface WeatherMapper: Mapper.DataToUI<String, LoadStates> {

    class Base @Inject constructor(
        private val resources: Resources,
        private val packageName: String
    ) : WeatherMapper {

        private val String.prefix
        get() = if (startsWith("+")) "p" else "m"

        private val String.editString
        get() = round(
            substring(1)
                .replace(",", ".").toFloat()
        ).toInt()

        private val String.getIdentifier
        get() = resources.getIdentifier(
            "${prefix}$editString", "raw", packageName
        )

        private val String.checkForOperator
        get() = !substring(0, 1).isDigitsOnly()

        override fun map(request: String?): LoadStates = with(request) {
            if (isNullOrEmpty()) LoadStates.OnError
            else if (checkForOperator)
                LoadStates.OnSuccess(getIdentifier, this)
            else LoadStates.OnSuccess(R.raw.zero, "0")
        }

        override fun map(throwable: Throwable) = LoadStates.OnError
    }
}



