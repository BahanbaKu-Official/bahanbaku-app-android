package com.bangkit.bahanbaku.presentation.snapfood.result

import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.data.repository.FoodRepository
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SnapFoodResultViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getToken() = profileUseCase.getToken()

    fun snapFood(token: String, file: File) = foodRepository.postSnapFood(token, file)
}