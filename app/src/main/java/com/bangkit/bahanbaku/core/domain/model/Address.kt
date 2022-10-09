package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val zipCode: String,
    val province: String,
    val city: String,
    val district: String,
    val subDistrict: String
) : Parcelable
