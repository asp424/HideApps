package com.lm.hideapps.di.dagger.modules

import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.notifications.Notifications
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationsModule {

    @Singleton
    @Provides
    fun providesNotifications(
        resources: Resources,
        notificationBuilder: NotificationCompat.Builder,
        notificationManagerCompat: NotificationManagerCompat
    ) = Notifications(resources, notificationBuilder, notificationManagerCompat)
}