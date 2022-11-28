package com.bangkit.bahanbaku.core.domain.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.PaymentItem
import io.reactivex.Flowable

interface IUtilRepository {
    fun getPaymentMethods(token: String): Flowable<Resource<List<PaymentItem>>>
}