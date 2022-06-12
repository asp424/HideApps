package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.sources.speech_recognize.SpeechRecognizeProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SpeechRecognizeProviderModule {

    @Binds
    @Singleton
    fun bindsSpeechRecognizeProvider(speechRecognizeProvider: SpeechRecognizeProvider.Base)
    : SpeechRecognizeProvider
}