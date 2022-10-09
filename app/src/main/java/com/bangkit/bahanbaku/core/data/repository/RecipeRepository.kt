package com.bangkit.bahanbaku.core.data.repository

import com.bangkit.bahanbaku.core.data.NetworkBoundResource
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.remote.ApiResponse
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
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
        object : NetworkBoundResource<List<Recipe>, List<RecipeItem>>(appExecutors) {
            override fun createCall(): Flowable<ApiResponse<List<RecipeItem>>> {
                return remoteDataSource.getNewRecipes(token)
            }

            override fun loadFromDB(): Flowable<List<Recipe>> {
                return localDataSource.getAllRecipes().map {
                    DataMapper.mapRecipeEntitiesToRecipeDomain(it)
                }
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean = data == null || data.isEmpty()

            override fun saveCallResult(data: List<RecipeItem>) {
                val recipeList = DataMapper.mapRecipeResponseToRecipeEntity(data)
                localDataSource.insertRecipes(recipeList)
            }

        }.asFlowable()

    override fun searchRecipe(token: String, query: String): Flowable<Resource<List<Recipe>>> =
        remoteDataSource.searchRecipe(token, query)

    override fun getRecipeById(token: String, id: String): Flowable<Resource<Recipe>> =
        object : NetworkBoundResource<Recipe, List<RecipeItem>>(appExecutors) {
            override fun createCall(): Flowable<ApiResponse<List<RecipeItem>>> {
                return remoteDataSource.getNewRecipes(token)
            }

            override fun loadFromDB(): Flowable<Recipe> {
                return localDataSource.getRecipeById(id).map {
                    DataMapper.mapRecipeEntitiesToRecipeDomain(it)
                }
            }

            override fun shouldFetch(data: Recipe?): Boolean = data == null

            override fun saveCallResult(data: List<RecipeItem>) {
                val recipeList = DataMapper.mapRecipeResponseToRecipeEntity(data)
                localDataSource.insertRecipes(recipeList)
            }

        }.asFlowable()
}