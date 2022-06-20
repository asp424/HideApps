package com.lm.hideapps.di.dagger.modules

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.di.dagger.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class NotificationManagerModule {

    @Provides
    @AppScope
    fun providesNotificationManager(context: Application) =
        NotificationManagerCompat.from(context)
}