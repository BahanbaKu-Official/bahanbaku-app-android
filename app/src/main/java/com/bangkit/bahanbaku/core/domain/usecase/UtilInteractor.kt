package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.PaymentItem
import com.bangkit.bahanbaku.core.domain.repository.IUtilRepository
import io.reactivex.Flowable

class UtilInteractor(private val utilRepository: IUtilRepository) : UtilUseCase {

    override fun getPaymentMethods(token: String): Flowable<Resource<List<PaymentItem>>> =
        utilRepository.getPaymentMethods(token)
}