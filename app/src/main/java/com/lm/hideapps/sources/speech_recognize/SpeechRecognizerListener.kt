package com.lm.hideapps.sources.speech_recognize

import android.os.Bundle
import android.speech.RecognitionListener

class SpeechRecognizerListener(
    val onError_: (Int) -> Unit = {},
    val onReadyForSpeech_: (Bundle) -> Unit = {},
    val onEndOfSpeech_: () -> Unit = {},
    val onResults_: (Bundle) -> Unit = {},
    val onPartialResults_: (Bundle) -> Unit = {},
    val onBufferReceived_: (ByteArray) -> Unit = {},
    val onEvent_: (Int, Bundle) -> Unit = { _, _ -> }
) : RecognitionListener {
    override fun onReadyForSpeech(bundle: Bundle?) { bundle?.let { onReadyForSpeech_(it) } }
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) { buffer?.let { onBufferReceived_(it) } }
    override fun onEndOfSpeech() { onEndOfSpeech_() }
    override fun onError(error: Int) { onError_(error) }
    override fun onResults(results: Bundle?) { results?.let { onResults_(it) } }
    override fun onPartialResults(bundle: Bundle?) { bundle?.let { onPartialResults_(it) } }
    override fun onEvent(eventType: Int, bundle: Bundle?) { bundle?.let { onEvent_(eventType, it) } }
}