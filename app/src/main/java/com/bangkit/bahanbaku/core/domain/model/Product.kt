package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val createdAt: String,
    val deletedAt: String,
    val productId: String,
    val price: Int,
    val name: String,
    val stock: Int,
    val updatedAt: String,
    val productImage: String,
    var quantity: Int
) : Parcelable