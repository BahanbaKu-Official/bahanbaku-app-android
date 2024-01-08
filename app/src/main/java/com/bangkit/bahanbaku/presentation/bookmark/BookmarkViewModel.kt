package com.bangkit.bahanbaku.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getBookmarks(token: String) =
        recipeUseCase.getFavorites(token).toLiveData()

    fun getToken() = profileUseCase.getToken()

    fun deleteBookmarks(token: String, position: Int) =
        recipeUseCase.deleteFavorites(token, position).toLiveData()
}