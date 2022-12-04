package com.bangkit.bahanbaku.presentation.directpayment

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
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
        LiveDataReactiveStreams.fromPublisher(
            paymentUseCase.createDirectPayment(
                token,
                products,
                id
            )
        )

    fun getDirectPaymentInfo(token: String) =
        LiveDataReactiveStreams.fromPublisher(paymentUseCase.getDirectPaymentInfo(token))

    fun getAddress() = profileUseCase.getMainAddress()
}