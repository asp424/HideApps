package com.lm.hideapps.core

interface Mapper<S, I> {
    fun map(string: S, type: S): I
}