package com.bangkit.bahanbaku.presentation.addressmaps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressMapsViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getProfile(token: String) =
        profileUseCase.getProfile(token).toLiveData()
}