package com.bangkit.bahanbaku.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {
    fun login(email: String, password: String) =
        profileUseCase.login(email, password).toLiveData()

    fun saveToken(token: String) {
        profileUseCase.saveToken(token)
    }

    fun isFirstTime() = profileUseCase.isFirstTime()

    fun setFirstTime(firstTime: Boolean) = profileUseCase.setFirstTime(firstTime)
}
