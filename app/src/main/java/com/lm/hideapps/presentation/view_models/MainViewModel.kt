package com.lm.hideapps.presentation.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.hideapps.presentation.ui.screens.TemperatureStates
import com.lm.hideapps.service.LocalServiceBindState
import com.lm.hideapps.service.LocalServiceBinder
import com.lm.hideapps.service.LocalServiceController
import com.lm.hideapps.service.LocalServiceState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val localServiceController: LocalServiceController,
    private val localServiceBinder: LocalServiceBinder
) : ViewModel() {

    private val _temperature: MutableStateFlow<TemperatureStates> =
        MutableStateFlow(TemperatureStates.OnInit)

    val temperature get() = _temperature.asStateFlow()

    private val _serviceIsRunning = MutableStateFlow(localServiceController.isRunning())

    val serviceIsRunning get() = _serviceIsRunning.asStateFlow()

    private val _isBindToService = MutableStateFlow(LocalServiceBindState.UNBINDING)

    val isBindToService get() = _isBindToService.asStateFlow()

    fun startMicrophoneService() = localServiceController.startMicrophoneService {
        _serviceIsRunning.value = LocalServiceState.RUNNING
    }

    fun stopMicrophoneService() = localServiceController.stopMicrophoneService {
        _serviceIsRunning.value = LocalServiceState.STOPPED
    }

    fun bindToMicrophoneService() {
        bindingLocalServiceJob.value = viewModelScope.launch {
            localServiceBinder.bindToLocalService().collect {
                _isBindToService.value = LocalServiceBindState.BINDING
                it.temperatureForUI.collect { t -> _temperature.value = t }
            }
        }
    }

    fun unBindToMicrophoneService() {
        bindingLocalServiceJob.value.cancel()
        _isBindToService.value = LocalServiceBindState.UNBINDING
        _temperature.value = TemperatureStates.OnInit
    }

    private val bindingLocalServiceJob: MutableState<Job> by lazy {
        mutableStateOf(Job().apply { cancel() })
    }
}