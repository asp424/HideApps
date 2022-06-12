package com.lm.hideapps.di.dagger.modules

import android.app.Application
import android.speech.SpeechRecognizer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SpeechRecognizerCreatorModule {

    @Provides
    @Singleton
    fun providesSpeechRecognizerCreator(application: Application): SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(application)

}