package com.bangkit.bahanbaku.presentation.addressmapsdetails

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressMapsDetailsViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {

    fun addAddress(
        token: String,
        street: String,
        district: String,
        city: String,
        province: String,
        zipCode: Int,
        label: String,
        receiverName: String,
        receiverNumber: String,
    ) = LiveDataReactiveStreams.fromPublisher(
        profileUseCase.addAddress(
            token, street, district, city, province, zipCode, label, receiverName, receiverNumber
        )
    )

    fun getToken() = profileUseCase.getToken()
}