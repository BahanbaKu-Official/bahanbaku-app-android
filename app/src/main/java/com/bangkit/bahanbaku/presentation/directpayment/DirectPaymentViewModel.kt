package com.bangkit.bahanbaku.presentation.directpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import com.bangkit.bahanbaku.core.domain.usecase.PaymentUseCase
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectPaymentViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val paymentUseCase: PaymentUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun createDirectPayment(token: String, products: ProductsData, id: String) =
        paymentUseCase.createDirectPayment(
            token,
            products,
            id
        ).toLiveData()

    fun getDirectPaymentInfo(token: String) =
        paymentUseCase.getDirectPaymentInfo(token).toLiveData()

    fun getAddress() = profileUseCase.getMainAddress()
}