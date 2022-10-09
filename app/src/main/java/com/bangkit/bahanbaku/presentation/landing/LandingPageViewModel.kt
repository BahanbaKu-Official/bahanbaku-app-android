package com.bangkit.bahanbaku.presentation.landing

import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    fun setFirstTime(firstTime: Boolean) = profileUseCase.setFirstTime(firstTime)
}