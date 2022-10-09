package com.bangkit.bahanbaku.presentation.login

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {
    fun login(email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.login(email, password))

    fun saveToken(token: String) {
        profileUseCase.saveToken(token)
    }

    fun isFirstTime() = profileUseCase.isFirstTime()

    fun setFirstTime(firstTime: Boolean) = profileUseCase.setFirstTime(firstTime)
}
