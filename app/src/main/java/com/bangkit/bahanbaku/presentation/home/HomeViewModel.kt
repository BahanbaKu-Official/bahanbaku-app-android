package com.bangkit.bahanbaku.presentation.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
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
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.getNewRecipes(token))

    fun getRecipesByTag(token: String, tag: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.getRecipesByTag(token, tag))

//    fun getFeaturedRecipe(token: String) = recipeRepository.getFeaturedRecipe(token)

    fun getToken() = profileUseCase.getToken()

    fun getProfile(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getProfile(token))
}