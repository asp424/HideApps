package com.lm.hideapps.data.local_repositories.core

import android.os.Bundle
import android.speech.RecognitionListener

class SpeechRecognizeListener(
    val onError_: (Int) -> Unit = {},
    val onEndOfSpeech_: () -> Unit = {},
    val onPartialResults_: (Bundle) -> Unit = {},
) : RecognitionListener {
    override fun onReadyForSpeech(bundle: Bundle?) = Unit
    override fun onBeginningOfSpeech() = Unit
    override fun onRmsChanged(rmsdB: Float) = Unit
    override fun onBufferReceived(buffer: ByteArray?) = Unit
    override fun onResults(results: Bundle?) = Unit
    override fun onEvent(eventType: Int, bundle: Bundle?) = Unit
    override fun onError(error: Int) { onError_(error) }
    override fun onPartialResults(bundle: Bundle?) { bundle?.let { onPartialResults_(it) } }
    override fun onEndOfSpeech() { onEndOfSpeech_() }
}


