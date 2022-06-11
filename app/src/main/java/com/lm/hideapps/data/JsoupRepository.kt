package com.lm.hideapps.data

import android.content.res.Resources
import com.lm.hideapps.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface JsoupRepository {

    suspend fun gismeteoNowTemp(): Flow<String>

    class Base @Inject constructor(private val resources: Resources) : JsoupRepository {
        override suspend fun gismeteoNowTemp() =
            suspendCoroutine<Flow<String>> { continuation ->
                runCatching {
                    Jsoup.connect(resources.getString(R.string.gis_krymsk_now_url)).get()
                        .getElementsByClass(resources.getString(R.string.gis_temp_today))[0]?.text()
                }.onSuccess {
                    continuation.resume(flowOf(it.toString()))
                }.onFailure {
                    continuation.resume(flowOf("Err"))
                }
            }
    }
}