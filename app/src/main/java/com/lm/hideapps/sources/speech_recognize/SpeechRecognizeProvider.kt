package com.lm.hideapps.sources.speech_recognize

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent.*
import android.speech.SpeechRecognizer
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface SpeechRecognizeProvider {

    fun startRecognize(): Flow<List<String>>

    class Base @Inject constructor(
        private val isAvailable: () -> Boolean,
        private val speechRecognizer: SpeechRecognizer
    ) : SpeechRecognizeProvider {

        override fun startRecognize(): Flow<List<String>> = callbackFlow {
            if (isAvailable.invoke()) {
                speechRecognizer.apply {
                    setRecognitionListener(
                        SpeechRecognizerListener(
                            onPartialResults_ =
                            {
                                it.recognizedWords?.let { l -> trySendBlocking(l.toList()) } },

                            onEndOfSpeech_ = { trySendBlocking(listOf("true"))
                                this@callbackFlow.cancel()
                            }
                        )
                    )

                    startListening(speechRecognizerIntent)
                    awaitClose { stopListening(); destroy() }
                }
            }
        }

        private val speechRecognizerIntent by lazy {
            Intent(ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
                putExtra(EXTRA_PARTIAL_RESULTS, true)
                putExtra(EXTRA_LANGUAGE, "ru-RU")
                putExtra(EXTRA_MAX_RESULTS, 100)
            }
        }
        private val Bundle.recognizedWords get() = getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
    }
}