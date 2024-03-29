package com.bangkit.bahanbaku.presentation.register

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
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
    ) =
        LiveDataReactiveStreams.fromPublisher(
            profileUseCase.register(
                firstName,
                lastName,
                email,
                password,
                phoneNumber
            )
        )
}