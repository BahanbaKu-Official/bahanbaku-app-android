package com.bangkit.bahanbaku.presentation.paymentmethod

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import com.bangkit.bahanbaku.core.domain.usecase.UtilUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val utilUseCase: UtilUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getPaymentMethods(token: String) =
        LiveDataReactiveStreams.fromPublisher(utilUseCase.getPaymentMethods(token))
}