package com.bangkit.bahanbaku.presentation.orderdetail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.PaymentUseCase
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val paymentUseCase: PaymentUseCase
) : ViewModel() {
    fun getToken() = profileUseCase.getToken()

    fun getOrderDetail(token: String, id: String) =
        LiveDataReactiveStreams.fromPublisher(paymentUseCase.getDirectOrderDetail(token, id))
}