package com.bangkit.bahanbaku.core.data.repository

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.response.GetPaymentMethodResponse
import com.bangkit.bahanbaku.core.data.remote.response.PaymentItem
import com.bangkit.bahanbaku.core.domain.repository.IUtilRepository
import io.reactivex.Flowable

class UtilRepository(
    private val remoteDataSource: RemoteDataSource
) : IUtilRepository {

    override fun getPaymentMethods(token: String): Flowable<Resource<List<PaymentItem>>> =
        remoteDataSource.getPaymentMethods(token)
}