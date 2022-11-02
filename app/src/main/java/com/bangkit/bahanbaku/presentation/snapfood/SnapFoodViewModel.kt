package com.bangkit.bahanbaku.presentation.snapfood

import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.data.repository.FoodRepository
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SnapFoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val profileUseCase: ProfileUseCase
    ) : ViewModel() {



    fun getToken() = profileUseCase.getToken()
}