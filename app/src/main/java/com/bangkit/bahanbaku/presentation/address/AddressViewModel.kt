package com.bangkit.bahanbaku.presentation.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getAddress(token: String) =
        profileUseCase.getAddress(token).toLiveData()

    fun getProfile(token: String) =
        profileUseCase.getProfile(token).toLiveData()

    fun setMainAddress(addressId: String) = profileUseCase.setMainAddress(addressId)

    fun getMainAddress() = profileUseCase.getMainAddress()
}