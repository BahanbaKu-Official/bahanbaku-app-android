package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val firstName: String,
    val lastName: String,
    val createdAt: String,
    val isVerified: Boolean,
    val profileImage: String,
    val userId: String,
    val email: String,
    val updatedAt: String,
    val phoneNumber: String,
) : Parcelable
