package com.bangkit.bahanbaku.presentation.updatelocation

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateLocationViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun getToken() = profileUseCase.getToken()

    fun updateLocation(token: String, lat: Double, lng: Double) =
        LiveDataReactiveStreams.fromPublisher(profileUseCase.updateLocation(token, lng, lat))
}