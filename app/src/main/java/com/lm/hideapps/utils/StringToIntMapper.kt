package com.lm.hideapps.utils

import android.content.res.Resources
import com.lm.hideapps.core.Mapper
import javax.inject.Inject
import kotlin.math.round

class StringToIntMapper @Inject constructor(
    private val resources: Resources,
    private val packageName: String
) : Mapper<String, Int> {

    override fun map(string: String, type: String) = with(string) {
        if (isNotEmpty())
            with(round(substring(1).replace(",", ".").toFloat()).toInt()) {
                resources.getIdentifier(
                    if (startsWith("+")) "p$this" else "m$this", type, packageName
                )
            }
        else 0
    }
}



