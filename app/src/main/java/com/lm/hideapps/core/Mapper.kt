package com.lm.hideapps.core

interface Mapper<S, I> {
    fun stringToSoundId(string: S, type: S): I
}