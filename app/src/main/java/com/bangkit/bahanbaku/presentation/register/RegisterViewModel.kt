package com.bangkit.bahanbaku.presentation.register

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) : ViewModel() {
    fun register(username: String, email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.register(username, email, password))
}