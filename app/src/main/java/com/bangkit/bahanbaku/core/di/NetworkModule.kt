package com.bangkit.bahanbaku.core.di

import android.content.Context
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiConfig
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiService
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiServiceML
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return ApiConfig.getApiService(context)
    }

    @Provides
    @Singleton
    fun provideApiServiceML(): ApiServiceML {
        return ApiConfig.getApiServiceML()
    }
}