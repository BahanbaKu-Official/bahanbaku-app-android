package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.DeleteFavoriteResponse
import com.bangkit.bahanbaku.core.data.remote.response.FavoriteItem
import com.bangkit.bahanbaku.core.data.remote.response.PostAddFavoriteResponse
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository
import io.reactivex.Flowable

class RecipeInteractor(private val recipeRepository: IRecipeRepository) : RecipeUseCase {
    override fun getNewRecipes(token: String) = recipeRepository.getNewRecipes(token)

    override fun searchRecipe(token: String, query: String) =
        recipeRepository.searchRecipe(token, query)

    override fun getRecipeById(token: String, id: String) =
        recipeRepository.getRecipeById(token, id)

    override fun getRecipesByTag(token: String, tag: String): Flowable<Resource<List<Recipe>>> =
        recipeRepository.getRecipeByTag(token, tag)

    override fun getFavorites(token: String): Flowable<Resource<List<FavoriteItem>>> =
        recipeRepository.getFavorites(token)

    override fun addFavorites(
        token: String,
        id: String
    ): Flowable<Resource<PostAddFavoriteResponse>> =
        recipeRepository.addFavorites(token, id)

    override fun deleteFavorites(
        token: String,
        position: Int
    ): Flowable<Resource<DeleteFavoriteResponse>> =
        recipeRepository.deleteFavorites(token, position)

    override fun deleteFavorites(
        token: String,
        id: String
    ): Flowable<Resource<DeleteFavoriteResponse>> = recipeRepository.deleteFavorites(token, id)

    override fun checkIfRecipeBookmarked(token: String, id: String): Flowable<Boolean> =
        recipeRepository.checkIfRecipeFavorited(token, id)
}