package com.bangkit.bahanbaku.core.domain.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import io.reactivex.Flowable
import java.io.File

interface IPaymentRepository {
    fun createDirectPayment(
        token: String,
        products: ProductsData,
        id: String
    ): Flowable<Resource<PostCreateDirectPaymentResponse>>

    fun submitPaymentProof(token: String, file: File, id: String): Flowable<Resource<PostSubmitProofResponse>>
    fun getDirectPaymentInfo(token: String): Flowable<Resource<GetDirectPaymentInfoResponse>>
    fun getDirectOrderHistory(token: String): Flowable<Resource<List<OrderHistoryItem>>>
    fun getDirectOrderDetail(
        token: String,
        id: String
    ): Flowable<Resource<DirectPaymentDetailResult>>
}