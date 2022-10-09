package com.bangkit.bahanbaku.presentation.camera

import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.data.repository.FoodRepository
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun postSnapFood(token: String, file: File) = repository.postSnapFood(token, file)

    fun getToken() = profileUseCase.getToken()
}