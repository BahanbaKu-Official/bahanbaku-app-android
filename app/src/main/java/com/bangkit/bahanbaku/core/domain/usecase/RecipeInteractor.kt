package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.domain.repository.IRecipeRepository

class RecipeInteractor(private val recipeRepository: IRecipeRepository) : RecipeUseCase {
    override fun getNewRecipes(token: String) = recipeRepository.getNewRecipes(token)

    override fun searchRecipe(token: String, query: String) = recipeRepository.searchRecipe(token, query)

    override fun getRecipeById(token: String, id: String) = recipeRepository.getRecipeById(token, id)

}