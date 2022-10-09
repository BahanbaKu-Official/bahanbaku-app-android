package com.bangkit.bahanbaku.core.di

import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.local.datastore.UserPreferences
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.repository.ProfileRepository
import com.bangkit.bahanbaku.core.data.repository.RecipeRepository
import com.bangkit.bahanbaku.core.domain.repository.IProfileRepository
import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository
import com.bangkit.bahanbaku.core.domain.usecase.ProfileInteractor
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeInteractor
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import com.bangkit.bahanbaku.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        appExecutors: AppExecutors
    ): IRecipeRepository {
        return RecipeRepository(localDataSource, remoteDataSource, appExecutors)
    }

    @Provides
    fun provideRecipeUseCase(repository: IRecipeRepository): RecipeUseCase {
        return RecipeInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        userPreferences: UserPreferences,
        appExecutors: AppExecutors
    ): IProfileRepository {
        return ProfileRepository(localDataSource, remoteDataSource, userPreferences, appExecutors)
    }

    @Provides
    fun provideProfileUseCase(repository: IProfileRepository): ProfileUseCase {
        return ProfileInteractor(repository)
    }
}