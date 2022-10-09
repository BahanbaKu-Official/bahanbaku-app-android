package com.bangkit.bahanbaku.presentation.search

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun searchRecipe(token: String, search: String) =
        LiveDataReactiveStreams.fromPublisher(recipeUseCase.searchRecipe(token, search))

    fun getToken() = profileUseCase.getToken()
}