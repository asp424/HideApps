package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.di.dagger.scopes.AppScope
import com.lm.hideapps.notifications.Notifications
import dagger.Binds
import dagger.Module

@Module
interface NotificationsModule {

    @Binds
    @AppScope
    fun bindsNotifications(notifications: Notifications.Base): Notifications
}