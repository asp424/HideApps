package com.lm.hideapps.core

import android.content.res.Resources
import javax.inject.Inject
import kotlin.math.round

class StringToIntMapper @Inject constructor(
    private val resources: Resources,
    private val packageName: String
) : Mapper<String, Int> {

    override fun stringToSoundId(string: String, type: String) = with(string) {
        if (isNotEmpty())
            with(round(substring(1).replace(",", ".").toFloat()).toInt()) {
                resources.getIdentifier(
                    if (startsWith("+")) "p$this" else "m$this", type, packageName
                )
            }
        else 0
    }
}



