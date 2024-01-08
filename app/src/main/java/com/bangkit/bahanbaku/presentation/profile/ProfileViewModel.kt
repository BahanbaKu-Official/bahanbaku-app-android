package com.bangkit.bahanbaku.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getProfile(token: String) =
        profileUseCase.getProfile(token).toLiveData()

    fun updateProfile(token: String, firstName: String, lastName: String) =
        profileUseCase.updateUser(
            token,
            firstName,
            lastName
        ).toLiveData()

    fun uploadPicture(token: String, file: File) =
        profileUseCase.uploadPicture(token, file).toLiveData()

    fun getToken() = profileUseCase.getToken()

    fun deleteToken() = profileUseCase.deleteToken()
}