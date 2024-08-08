package com.jy.mob21firebase.core.di

import android.content.Context
import com.jy.mob21firebase.core.services.AuthService
import com.jy.mob21firebase.core.services.StorageService
import com.jy.mob21firebase.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthService(@ApplicationContext context: Context): AuthService = AuthService(context)

    @Provides
    @Singleton
    fun provideStorageService(authService: AuthService): StorageService = StorageService(authService)
}