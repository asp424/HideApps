package com.lm.hideapps.data.remote_repositories

sealed class LoadWeatherStates {
    class OnSuccess(val soundId: Int, val temperatureAsString: String) : LoadWeatherStates()
    object OnError : LoadWeatherStates()
}