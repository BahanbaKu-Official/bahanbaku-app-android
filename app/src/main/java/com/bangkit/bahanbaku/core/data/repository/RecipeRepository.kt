package com.bangkit.bahanbaku.core.data.repository

import com.bangkit.bahanbaku.core.data.NetworkBoundResource
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.remote.ApiResponse
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.response.RecipeDetailItem
import com.bangkit.bahanbaku.core.data.remote.response.RecipeItem
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository
import com.bangkit.bahanbaku.core.utils.AppExecutors
import com.bangkit.bahanbaku.core.utils.DataMapper
import io.reactivex.Flowable

class RecipeRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IRecipeRepository {
    override fun getNewRecipes(token: String): Flowable<Resource<List<Recipe>>> =
        remoteDataSource.getNewRecipes(token)

    override fun searchRecipe(token: String, query: String): Flowable<Resource<List<Recipe>>> =
        remoteDataSource.searchRecipe(token, query)

    override fun getRecipeById(token: String, id: String): Flowable<Resource<RecipeDetailItem>> =
        remoteDataSource.getRecipeById(token, id)
}