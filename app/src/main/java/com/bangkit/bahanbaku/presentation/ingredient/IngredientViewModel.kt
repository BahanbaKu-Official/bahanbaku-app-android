package com.bangkit.bahanbaku.presentation.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.IngredientResults
import com.bangkit.bahanbaku.core.data.repository.IngredientRepository
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val repository: IngredientRepository,
    private val profileUseCase: ProfileUseCase
) :
    ViewModel() {
    fun getIngredients(token: String, search: List<String>): LiveData<Resource<IngredientResults>> {
        var searchStr = ""
        search.forEachIndexed { index, s ->
            searchStr += if (index == search.lastIndex) {
                s
            } else {
                "$s,"
            }
        }

        return repository.getIngredient(token, searchStr)
    }

    fun getToken() = profileUseCase.getToken()
}