package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.core.Permissions
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface PermissionsModule {

    @Binds
    @Singleton
    fun bindsPermissions(permissions: Permissions.Base): Permissions
}