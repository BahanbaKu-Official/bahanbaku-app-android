package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val createdAt: String = "",
//    val deletedAt: String,
    val author: String = "",
    val imageUrl: String = "",
    val portion: Int = 0,
    val rating: Double = 0.0,
    val description: String = "",
    val time: Int = 0,
    val title: String = "",
    val recipeId: String = "",
    val updatedAt: String = "",
    val imageResource: Int = 0,
) : Parcelable
