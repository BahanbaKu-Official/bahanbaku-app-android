package com.bangkit.bahanbaku.presentation.profile

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getProfile(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getProfile(token))

    fun updateProfile(token: String, firstName: String, lastName: String) =
        LiveDataReactiveStreams.fromPublisher(
            profileUseCase.updateUser(
                token,
                firstName,
                lastName
            )
        )

    fun uploadPicture(token: String, file: File) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.uploadPicture(token, file))

    fun getToken() = profileUseCase.getToken()

    fun deleteToken() = profileUseCase.deleteToken()
}