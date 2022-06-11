package com.lm.hideapps.utils

import com.lm.hideapps.R
import kotlin.math.absoluteValue
import kotlin.math.round

fun String.getSound() = when (prepareString) {
    10 -> R.raw.p10
    11 -> R.raw.p11
    12 -> R.raw.p12
    13 -> R.raw.p13
    14 -> R.raw.p14
    15 -> R.raw.p15
    16 -> R.raw.p16
    17 -> R.raw.p17
    18 -> R.raw.p18
    19 -> R.raw.p19
    20 -> R.raw.p20
    21 -> R.raw.p21
    22 -> R.raw.p22
    23 -> R.raw.p23
    24 -> R.raw.p24
    25 -> R.raw.p25
    26 -> R.raw.p26
    27 -> R.raw.p27
    28 -> R.raw.p28
    29 -> R.raw.p29
    30 -> R.raw.p30
    31 -> R.raw.p31
    32 -> R.raw.p32
    33 -> R.raw.p33
    34 -> R.raw.p34
    35 -> R.raw.p35
    else -> R.raw.p35
}

val String.prepareString
    get() = if (isNotEmpty())
        round(substring(1).replace(",", ".").toFloat()).toInt()
    else 1000