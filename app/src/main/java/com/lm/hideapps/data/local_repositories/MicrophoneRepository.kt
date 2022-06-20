package com.lm.hideapps.data.local_repositories

import android.Manifest
import android.content.Intent
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.lm.hideapps.core.Permissions
import com.lm.hideapps.data.local_repositories.core.SpeechRecognizeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject


interface MicrophoneRepository {

    fun recognizedWordsAsFlow(language: String): Flow<List<String>>

    fun microphoneLevelAsFlow(
        sampleRateHz: Int, channelConfig: Int, audioFormat: Int
    ): Flow<Int>

    class Base @Inject constructor(
        private val speechRecognizerAvailable: () -> Boolean,
        private val speechRecognizer: SpeechRecognizer,
        private val permissions: Permissions
    ): MicrophoneRepository {

        override fun recognizedWordsAsFlow(language: String): Flow<List<String>>
        = callbackFlow {
            if (speechRecognizerAvailable.invoke()) {
                speechRecognizer.apply {
                    setRecognitionListener(
                        SpeechRecognizeListener(
                            onPartialResults_ =
                            { bundle ->
                                bundle.recognizedWords?.let { trySendBlocking(it.toList()) }
                            },
                            onEndOfSpeech_ = {
                                trySendBlocking(listOf("true"))
                                this@callbackFlow.cancel()
                            }
                        )
                    )
                    startListening(speechRecognizerIntent(language))
                    awaitClose { stopListening(); destroy() }
                }
            }
        }

        override fun microphoneLevelAsFlow(sampleRateHz: Int, channelConfig: Int, audioFormat: Int) =
            callbackFlow {
                with(permissions) {
                    if (checkSinglePermission(Manifest.permission.RECORD_AUDIO)) {
                        AudioRecord.getMinBufferSize(sampleRateHz, channelConfig, audioFormat)
                            .also { minBufferSize ->
                                ShortArray(minBufferSize).also { buffer ->
                                    AudioRecord(
                                        MediaRecorder.AudioSource.MIC,
                                        sampleRateHz,
                                        channelConfig,
                                        audioFormat,
                                        minBufferSize
                                    ).apply {
                                        startRecording()
                                        while (isActive) {
                                            read(buffer, 0, minBufferSize)
                                            trySendBlocking(StrictMath.abs(buffer.maxOf { it }
                                                .toInt()))
                                        }
                                        awaitClose { stop(); release() }
                                    }
                                }
                            }
                    }
                }
            }.flowOn(Dispatchers.IO)

        private fun speechRecognizerIntent(language: String) =
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100)
            }

        private val Bundle.recognizedWords
            get() = getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

    }
}