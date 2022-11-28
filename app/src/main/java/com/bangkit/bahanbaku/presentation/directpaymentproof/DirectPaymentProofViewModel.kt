package com.bangkit.bahanbaku.presentation.directpaymentproof

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.PaymentUseCase
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DirectPaymentProofViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val paymentUseCase: PaymentUseCase
) : ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun submitPaymentProof(token: String, file: File, id: String) =
        LiveDataReactiveStreams.fromPublisher(paymentUseCase.submitPaymentProof(token, file, id))
}