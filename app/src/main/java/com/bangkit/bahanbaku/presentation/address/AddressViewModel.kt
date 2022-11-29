package com.bangkit.bahanbaku.presentation.address

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getAddress(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getAddress(token))

    fun getProfile(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getProfile(token))

    fun setMainAddress(addressId: String) = profileUseCase.setMainAddress(addressId)

    fun getMainAddress() = profileUseCase.getMainAddress()
}