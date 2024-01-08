package com.bangkit.bahanbaku.presentation.addressmapsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
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
    ) = profileUseCase.addAddress(
        token, street, district, city, province, zipCode, label, receiverName, receiverNumber
    ).toLiveData()

    fun getAddress(token: String) =
        profileUseCase.getAddress(token).toLiveData()

    fun setMainAddress(addressId: String) = profileUseCase.setMainAddress(addressId)

    fun getToken() = profileUseCase.getToken()
}