package com.lm.hideapps.data.remote_repositories

import androidx.core.text.isDigitsOnly
import com.lm.hideapps.R
import com.lm.hideapps.core.Mapper
import com.lm.hideapps.core.ResourcesManager
import com.lm.hideapps.core.log
import javax.inject.Inject
import kotlin.math.round

interface WeatherMapper : Mapper.DataToUI<String, LoadWeatherStates> {

    class Base @Inject constructor(
        private val resourcesManager: ResourcesManager
    ) : WeatherMapper {

        override fun map(request: String?): LoadWeatherStates = with(request) {
            if (isNullOrEmpty()){
                LoadWeatherStates.OnError
            }
            else {
                if (checkForOperator) {
                    LoadWeatherStates.OnSuccess(getIdentifier, this)
                }
                else{
                    LoadWeatherStates.OnSuccess(R.raw.zero, "0")
                }
            }
        }

        override fun map(throwable: Throwable) = LoadWeatherStates.OnError

        private val String.prefix
            get() = if (startsWith("+")) "p" else "m"

        private val String.editString
            get() = round(
                substring(1).replace(",", ".").toFloat()
            ).toInt()

        private val String.getIdentifier
        get() = resourcesManager.raw("$prefix$editString")

        private val String.checkForOperator
            get() = !substring(0, 1).isDigitsOnly()
    }
}



