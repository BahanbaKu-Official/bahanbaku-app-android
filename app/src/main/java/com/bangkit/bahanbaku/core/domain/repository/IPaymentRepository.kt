package com.bangkit.bahanbaku.core.domain.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.GetDirectPaymentInfoResponse
import com.bangkit.bahanbaku.core.data.remote.response.PostCreateDirectPaymentResponse
import com.bangkit.bahanbaku.core.data.remote.response.PostSubmitProofResponse
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import io.reactivex.Flowable
import java.io.File

interface IPaymentRepository {
    fun createDirectPayment(
        token: String,
        products: ProductsData
    ): Flowable<Resource<PostCreateDirectPaymentResponse>>

    fun submitPaymentProof(token: String, file: File, id: String): Flowable<Resource<PostSubmitProofResponse>>
    fun getDirectPaymentInfo(token: String): Flowable<Resource<GetDirectPaymentInfoResponse>>
}