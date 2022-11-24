package com.bangkit.bahanbaku.presentation.checkout

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val profileUseCase: ProfileUseCase) :
    ViewModel() {

    fun getToken() = profileUseCase.getToken()

    fun getAddress(token: String) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.getAddress(token))
}