package com.lm.hideapps.data.remote_repositories

sealed class LoadWeatherStates {
    class OnSuccess(val id: Int, val temperature: String) : LoadWeatherStates()
    object OnError : LoadWeatherStates()
}