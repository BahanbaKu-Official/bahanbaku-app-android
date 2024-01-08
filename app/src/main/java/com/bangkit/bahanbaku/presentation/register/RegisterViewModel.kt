package com.bangkit.bahanbaku.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ) = profileUseCase.register(
        firstName,
        lastName,
        email,
        password,
        phoneNumber
    ).toLiveData()
}