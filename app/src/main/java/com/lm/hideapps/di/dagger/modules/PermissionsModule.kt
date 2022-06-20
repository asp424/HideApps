package com.lm.hideapps.di.dagger.modules

import com.lm.hideapps.core.Permissions
import com.lm.hideapps.di.dagger.scopes.AppScope
import dagger.Binds
import dagger.Module

@Module
interface PermissionsModule {

    @Binds
    @AppScope
    fun bindPermissions(permissions: Permissions.Base): Permissions
}