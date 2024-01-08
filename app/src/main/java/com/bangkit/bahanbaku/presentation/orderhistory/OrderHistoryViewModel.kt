package com.bangkit.bahanbaku.presentation.orderhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.bangkit.bahanbaku.core.domain.usecase.PaymentUseCase
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val paymentUseCase: PaymentUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getDirectOrderHistory(token: String) =
        paymentUseCase.getDirectOrderHistory(token).toLiveData()
}