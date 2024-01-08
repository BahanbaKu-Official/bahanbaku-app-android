package com.bangkit.bahanbaku.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getRecipes(token: String) =
        recipeUseCase.getNewRecipes(token).toLiveData()

    fun getRecipesByTag(token: String, tag: String) =
        recipeUseCase.getRecipesByTag(token, tag).toLiveData()

//    fun getFeaturedRecipe(token: String) = recipeRepository.getFeaturedRecipe(token)

    fun getToken() = profileUseCase.getToken()

    fun getProfile(token: String) =
        profileUseCase.getProfile(token).toLiveData()
}