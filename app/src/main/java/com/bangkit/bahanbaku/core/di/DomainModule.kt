package com.bangkit.bahanbaku.core.di

import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.local.datastore.UserPreferences
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.repository.PaymentRepository
import com.bangkit.bahanbaku.core.data.repository.ProfileRepository
import com.bangkit.bahanbaku.core.data.repository.RecipeRepository
import com.bangkit.bahanbaku.core.data.repository.UtilRepository
import com.bangkit.bahanbaku.core.domain.repository.IPaymentRepository
import com.bangkit.bahanbaku.core.domain.repository.IProfileRepository
import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository
import com.bangkit.bahanbaku.core.domain.repository.IUtilRepository
import com.bangkit.bahanbaku.core.domain.usecase.*
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

    @Provides
    @Singleton
    fun provideUtilRepository(
        remoteDataSource: RemoteDataSource
    ): IUtilRepository {
        return UtilRepository(remoteDataSource)
    }

    @Provides
    fun provideUtilUseCase(repository: IUtilRepository): UtilUseCase {
        return UtilInteractor(repository)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(
        remoteDataSource: RemoteDataSource
    ): IPaymentRepository {
        return PaymentRepository(remoteDataSource)
    }

    @Provides
    fun providePaymentUseCase(repository: IPaymentRepository): PaymentUseCase {
        return PaymentInteractor(repository)
    }
}