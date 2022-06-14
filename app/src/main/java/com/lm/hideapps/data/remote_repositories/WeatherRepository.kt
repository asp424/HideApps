package com.lm.hideapps.data.remote_repositories

import android.content.res.Resources
import com.lm.hideapps.R
import com.lm.hideapps.core.WeatherMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepository @Inject constructor(
    private val resources: Resources,
    private val weatherMapper: WeatherMapper
    ) {

    suspend fun nowTemperature() =
        suspendCoroutine<Flow<LoadStates>> { continuation ->
            runCatching {
                Jsoup.connect(resources.getString(R.string.gis_krymsk_now_url)).get()
                    .getElementsByClass(resources.getString(R.string.gis_temp_today))[0]?.text()
            }.onSuccess {
                continuation.resume(flowOf(weatherMapper.map(it)))
            }.onFailure {
                continuation.resume(flowOf(weatherMapper.map(it)))
            }
        }
}
