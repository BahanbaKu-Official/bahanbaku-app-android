package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.GetDirectPaymentInfoResponse
import com.bangkit.bahanbaku.core.data.remote.response.PostSubmitProofResponse
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import com.bangkit.bahanbaku.core.domain.repository.IPaymentRepository
import io.reactivex.Flowable
import java.io.File

class PaymentInteractor(private val paymentRepository: IPaymentRepository) : PaymentUseCase {
    override fun createDirectPayment(token: String, products: ProductsData) =
        paymentRepository.createDirectPayment(token, products)

    override fun submitPaymentProof(
        token: String,
        file: File,
        id: String
    ): Flowable<Resource<PostSubmitProofResponse>> =
        paymentRepository.submitPaymentProof(token, file, id)

    override fun getDirectPaymentInfo(token: String): Flowable<Resource<GetDirectPaymentInfoResponse>> =
        paymentRepository.getDirectPaymentInfo(token)
}