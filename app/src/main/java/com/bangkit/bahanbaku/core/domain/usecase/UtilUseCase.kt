package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.PaymentItem
import io.reactivex.Flowable

interface UtilUseCase {
    fun getPaymentMethods(token: String): Flowable<Resource<List<PaymentItem>>>
}