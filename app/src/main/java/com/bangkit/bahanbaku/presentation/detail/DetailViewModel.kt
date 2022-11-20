package com.bangkit.bahanbaku.presentation.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
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
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.getRecipeById(token, id))

    fun checkIfRecipeBookmarked(token: String, id: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.checkIfRecipeBookmarked(token, id))

    fun addBookmark(token: String, id: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.addFavorites(token, id))

    fun deleteBookmark(token: String, id: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.deleteFavorites(token, id))

    fun getToken() = profileUseCase.getToken()

    fun getProfile(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getProfile(token))
}