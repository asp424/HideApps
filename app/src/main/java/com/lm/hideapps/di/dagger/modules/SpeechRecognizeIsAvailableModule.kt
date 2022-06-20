package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.speech.SpeechRecognizer
import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.di.dagger.scopes.MicrophoneServiceScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SpeechRecognizeIsAvailableModule {

    @Provides
    @AppScope
    fun providesSpeechRecognizeIsAvailable(application: Application): () -> Boolean =
        { SpeechRecognizer.isRecognitionAvailable(application) }
}