package com.bangkit.bahanbaku.core.data.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.response.GetDirectPaymentInfoResponse
import com.bangkit.bahanbaku.core.data.remote.response.OrderHistoryItem
import com.bangkit.bahanbaku.core.data.remote.response.PostCreateDirectPaymentResponse
import com.bangkit.bahanbaku.core.data.remote.response.PostSubmitProofResponse
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import com.bangkit.bahanbaku.core.domain.repository.IPaymentRepository
import io.reactivex.Flowable
import java.io.File

class PaymentRepository(private val remoteDataSource: RemoteDataSource) : IPaymentRepository {
    override fun getDirectPaymentInfo(token: String): Flowable<Resource<GetDirectPaymentInfoResponse>> =
        remoteDataSource.getDirectPaymentInfo(token)

    override fun createDirectPayment(
        token: String,
        products: ProductsData,
    ): Flowable<Resource<PostCreateDirectPaymentResponse>> =
        remoteDataSource.createDirectPayment(token, products)

    override fun submitPaymentProof(
        token: String,
        file: File,
        id: String
    ): Flowable<Resource<PostSubmitProofResponse>> =
        remoteDataSource.submitPaymentProof(token, file, id)

    override fun getDirectOrderHistory(
        token: String
    ): Flowable<Resource<List<OrderHistoryItem>>> = remoteDataSource.getDirectOrderHistory(token)
}