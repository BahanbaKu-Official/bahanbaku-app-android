package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val bookmarks: List<String>,
    val lat: Double,
    val lon: Double,
    val id: String,
    val email: String,
    val picture: String,
    val username: String
) : Parcelable
