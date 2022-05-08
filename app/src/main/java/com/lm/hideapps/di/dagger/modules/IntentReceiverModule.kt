package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.data.sources.IntentReceiver
import com.lm.hideapps.data.sources.IntentReceiverImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface IntentReceiverModule {
	
	
	@Binds
	@Singleton
	fun bindsIntentReceiver(intentReceiver: IntentReceiverImpl): IntentReceiver
	
}