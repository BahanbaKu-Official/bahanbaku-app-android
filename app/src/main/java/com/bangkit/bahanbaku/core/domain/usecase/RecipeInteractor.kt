package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository
import io.reactivex.Flowable

class RecipeInteractor(private val recipeRepository: IRecipeRepository) : RecipeUseCase {
    override fun getNewRecipes(token: String) = recipeRepository.getNewRecipes(token)

    override fun searchRecipe(token: String, query: String) = recipeRepository.searchRecipe(token, query)

    override fun getRecipeById(token: String, id: String) = recipeRepository.getRecipeById(token, id)

    override fun getRecipesByTag(token: String, tag: String): Flowable<Resource<List<Recipe>>> = recipeRepository.getRecipeByTag(token, tag)
}