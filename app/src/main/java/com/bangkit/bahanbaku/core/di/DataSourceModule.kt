package com.bangkit.bahanbaku.core.di

import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.local.room.ProfileDatabase
import com.bangkit.bahanbaku.core.data.local.room.RecipeDatabase
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource.getInstance(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        recipeDatabase: RecipeDatabase,
        profileDatabase: ProfileDatabase
    ): LocalDataSource {
        return LocalDataSource.getInstance(
            recipeDatabase.recipeDao(),
            profileDatabase.profileDao()
        )
    }
}