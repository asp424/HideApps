package com.lm.hideapps.data.remote_repositories

import com.lm.hideapps.R
import com.lm.hideapps.core.ResourcesManager
import com.lm.hideapps.core.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface WeatherRepository {

    suspend fun nowTemperature(): Flow<LoadWeatherStates>

    class Base @Inject constructor(
        private val resourcesManager: ResourcesManager,
        private val weatherMapper: WeatherMapper
    ) : WeatherRepository {

        override suspend fun nowTemperature() =
            suspendCoroutine<Flow<LoadWeatherStates>> { continuation ->
                runCatching {
                    Jsoup.connect(
                        resourcesManager.string(R.string.gis_krymsk_now_url)
                    ).get().getElementsByClass(
                        resourcesManager.string(R.string.gis_temp_today)
                    )[0]?.text()
                }.onSuccess {
                    continuation.resume(flowOf(weatherMapper.map(it)))
                }.onFailure {
                    continuation.resume(flowOf(weatherMapper.map(it)))
                }
            }
    }
}
