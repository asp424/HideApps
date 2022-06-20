package com.lm.hideapps.presentation.ui.screens

sealed class TemperatureStates {
    class OnSuccess(val temperature: String) : TemperatureStates()
    object OnLoading : TemperatureStates()
    object OnInit : TemperatureStates()
}