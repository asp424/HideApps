package com.lm.hideapps.sources.microphone

import android.Manifest.permission.RECORD_AUDIO
import android.media.AudioFormat.*
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize
import android.media.MediaRecorder.AudioSource.MIC
import com.lm.hideapps.core.Permissions
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject


interface Microphone {
    val getMicLevelWithDefault: Flow<Short>
    class Base @Inject constructor(private val permissions: Permissions) : Microphone {
        override val getMicLevelWithDefault
            get() = callbackFlow {
                with(permissions) {
                    if (checkSinglePermission(RECORD_AUDIO))
                        AudioRecord(MIC, config[0], config[1], config[2], minSize)
                            .apply {
                            while (isActive) {
                                startRecording()
                                read(buffer, 0, minSize)
                                trySendBlocking(buffer.maxOf { it })
                                stop()
                                delay(1)
                            }
                            awaitClose { release() }
                        }
                }
            }.flowOn(IO)

        private val minSize by lazy { getMinBufferSize(config[0], config[1], config[2]) }
        private val buffer by lazy { ShortArray(minSize) }
        private val config by lazy { listOf(8000, CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT) }
    }
}
