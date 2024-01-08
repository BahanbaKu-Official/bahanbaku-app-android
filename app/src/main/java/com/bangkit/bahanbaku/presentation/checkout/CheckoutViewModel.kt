package com.bangkit.bahanbaku.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getMainAddress() = profileUseCase.getMainAddress()

    fun getAddress(token: String) =
        profileUseCase.getAddress(token).toLiveData()

    fun getAddressById(token: String, id: String) =
        profileUseCase.getAddressById(token, id).toLiveData()
}