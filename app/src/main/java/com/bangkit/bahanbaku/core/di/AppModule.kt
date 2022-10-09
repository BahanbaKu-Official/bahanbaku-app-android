package com.bangkit.bahanbaku.core.di

import com.bangkit.bahanbaku.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppExecutors() = AppExecutors()
}