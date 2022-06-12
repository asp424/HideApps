package com.lm.hideapps.sources.microphone

import com.lm.hideapps.R
import com.lm.hideapps.data.JsoupRepository
import com.lm.hideapps.sources.microphone.MicServiceHandler.Base.ResourcesNames.RAW
import com.lm.hideapps.di.dagger.AppComponent
import com.lm.hideapps.utils.StringToIntMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MicServiceHandler {
    val temp: SharedFlow<String>
    var bind: Boolean
    suspend fun String.emit()
    fun micServiceJob(level: Int)
    fun cancelJob()

    class Base @Inject constructor(
        private val appComponent: AppComponent,
        private val jsoupRepository: JsoupRepository,
        private val mapper: StringToIntMapper,
        private val microphone: Microphone
    ) : MicServiceHandler {

        override suspend fun String.emit() { if (bind) tempFlow.emit(this) }

        override fun micServiceJob(level: Int) {
            job.cancel()
            if (!job.isActive) {
                job = CoroutineScope(IO).launch {
                    microphone.getMicLevelWithDefault.collect {
                        if (it > level && !isLoadingTemp) {
                            "true".emit()
                            isLoadingTemp = true
                            appComponent.playSound().invoke(R.raw.a) {}
                            jsoupRepository.gismeteoNowTemp().collect { temp ->
                                appComponent.playSound().invoke(mapper.map(temp, RAW))
                                { isLoadingTemp = false }
                                temp.emit()
                            }
                        }
                    }
                }
            }
        }

        override fun cancelJob() = job.cancel()
        override var bind = false
        private var job: Job = Job()
        private var isLoadingTemp = false
        override val temp get() = tempFlow.asSharedFlow()
        private val tempFlow =
            MutableSharedFlow<String>(0, 0, BufferOverflow.SUSPEND)

        private object ResourcesNames {
            const val RAW = "raw"
        }
    }
}
