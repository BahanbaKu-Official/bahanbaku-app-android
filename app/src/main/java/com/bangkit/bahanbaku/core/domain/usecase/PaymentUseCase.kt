package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import io.reactivex.Flowable
import java.io.File

interface PaymentUseCase {
    fun createDirectPayment(
        token: String,
        products: ProductsData,
        id: String
    ): Flowable<Resource<PostCreateDirectPaymentResponse>>
    fun getDirectPaymentInfo(token: String): Flowable<Resource<GetDirectPaymentInfoResponse>>
    fun submitPaymentProof(
        token: String,
        file: File,
        id: String
    ): Flowable<Resource<PostSubmitProofResponse>>

    fun getDirectOrderHistory(token: String): Flowable<Resource<List<OrderHistoryItem>>>
    fun getDirectOrderDetail(
        token: String,
        id: String
    ): Flowable<Resource<DirectPaymentDetailResult>>
}