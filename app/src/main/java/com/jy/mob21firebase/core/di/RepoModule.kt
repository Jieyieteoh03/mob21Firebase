package com.jy.mob21firebase.core.di

import com.jy.mob21firebase.core.services.AuthService
import com.jy.mob21firebase.data.repo.TodoRepo
import com.jy.mob21firebase.data.repo.TodoRepoFirestore
import com.jy.mob21firebase.data.repo.TodoRepoReal
import com.jy.mob21firebase.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun provideTodoRepo(authService: AuthService): TodoRepo =  TodoRepoFirestore(authService)

    @Provides
    @Singleton
    fun provideUserRepo(authService: AuthService): UserRepo = UserRepo(authService)
}