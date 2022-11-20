package com.bangkit.bahanbaku.core.domain.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.Recipe
import io.reactivex.Flowable

interface IRecipeRepository {
    fun getNewRecipes(token: String): Flowable<Resource<List<Recipe>>>
    fun searchRecipe(token: String, query: String): Flowable<Resource<List<Recipe>>>
    fun getRecipeById(token: String, id: String): Flowable<Resource<RecipeDetailItem>>
    fun getRecipeByTag(token: String, tag: String): Flowable<Resource<List<Recipe>>>
    fun getFavorites(token: String): Flowable<Resource<List<FavoriteItem>>>
    fun addFavorites(token: String, id: String): Flowable<Resource<PostAddFavoriteResponse>>
    fun deleteFavorites(token: String, position: Int): Flowable<Resource<DeleteFavoriteResponse>>
    fun deleteFavorites(token: String, id: String): Flowable<Resource<DeleteFavoriteResponse>>
    fun checkIfRecipeFavorited(token: String, id: String): Flowable<Boolean>
}