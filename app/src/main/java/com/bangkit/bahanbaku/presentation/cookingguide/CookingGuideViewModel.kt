package com.bangkit.bahanbaku.presentation.cookingguide

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CookingGuideViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getToken() = profileUseCase.getToken()

    fun getRecipeById(token: String, id: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.getRecipeById(token, id))
}