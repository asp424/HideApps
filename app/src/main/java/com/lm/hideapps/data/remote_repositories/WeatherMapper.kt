package com.lm.hideapps.data.remote_repositories

import android.content.res.Resources
import androidx.core.text.isDigitsOnly
import com.lm.hideapps.R
import com.lm.hideapps.core.Mapper
import javax.inject.Inject
import kotlin.math.round

interface WeatherMapper: Mapper.DataToUI<String, LoadWeatherStates> {

    class Base @Inject constructor(
        private val resources: Resources,
        private val packageName: String
    ) : WeatherMapper {

        override fun map(request: String?): LoadWeatherStates = with(request) {
            if (isNullOrEmpty()) LoadWeatherStates.OnError
            else if (checkForOperator)
                LoadWeatherStates.OnSuccess(getIdentifier, this)
            else LoadWeatherStates.OnSuccess(R.raw.zero, "0")
        }

        override fun map(throwable: Throwable) = LoadWeatherStates.OnError

        private val String.prefix
        get() = if (startsWith("+")) "p" else "m"

        private val String.editString
        get() = round(
            substring(1)
                .replace(",", ".").toFloat()
        ).toInt()

        private val String.getIdentifier
        get() = resources.getIdentifier(
            "$prefix$editString", "raw", packageName
        )

        private val String.checkForOperator
        get() = !substring(0, 1).isDigitsOnly()
    }
}



