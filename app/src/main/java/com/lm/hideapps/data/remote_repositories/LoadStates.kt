package com.lm.hideapps.data.remote_repositories

sealed class LoadStates {
    class OnSuccess(val id: Int, val temperature: String): LoadStates()
    object OnError: LoadStates()
}