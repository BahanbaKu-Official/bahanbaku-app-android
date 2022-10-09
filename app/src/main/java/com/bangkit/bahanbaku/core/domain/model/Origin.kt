package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origin(
    val lng: Double,
    val lat: Double
) : Parcelable
