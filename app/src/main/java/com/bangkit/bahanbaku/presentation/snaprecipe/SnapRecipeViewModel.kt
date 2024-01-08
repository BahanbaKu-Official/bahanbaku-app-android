package com.bangkit.bahanbaku.presentation.snaprecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SnapRecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getRecipes(token: String, foodName: String) =
        recipeUseCase.searchRecipe(token, foodName).toLiveData()
}