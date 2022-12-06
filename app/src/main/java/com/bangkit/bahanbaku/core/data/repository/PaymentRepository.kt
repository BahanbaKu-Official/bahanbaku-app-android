package com.bangkit.bahanbaku.core.data.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.response.*
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
        id: String
    ): Flowable<Resource<PostCreateDirectPaymentResponse>> =
        remoteDataSource.createDirectPayment(token, products, id)

    override fun submitPaymentProof(
        token: String,
        file: File,
        id: String
    ): Flowable<Resource<PostSubmitProofResponse>> =
        remoteDataSource.submitPaymentProof(token, file, id)

    override fun getDirectOrderHistory(
        token: String
    ): Flowable<Resource<List<OrderHistoryItem>>> = remoteDataSource.getDirectOrderHistory(token)

    override fun getDirectOrderDetail(
        token: String,
        id: String
    ): Flowable<Resource<DirectPaymentDetailResult>> =
        remoteDataSource.getDirectOrderDetail(token, id)
}