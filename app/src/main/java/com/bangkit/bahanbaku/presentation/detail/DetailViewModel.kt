package com.bangkit.bahanbaku.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getRecipe(token: String, id: String) =
        recipeUseCase.getRecipeById(token, id).toLiveData()

    fun checkIfRecipeBookmarked(token: String, id: String) =
        recipeUseCase.checkIfRecipeBookmarked(token, id).toLiveData()

    fun addBookmark(token: String, id: String) =
        recipeUseCase.addFavorites(token, id).toLiveData()

    fun deleteBookmark(token: String, id: String) =
        recipeUseCase.deleteFavorites(token, id).toLiveData()

    fun getToken() = profileUseCase.getToken()

    fun getProfile(token: String) =
        profileUseCase.getProfile(token).toLiveData()
}