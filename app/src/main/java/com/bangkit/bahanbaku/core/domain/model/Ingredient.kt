package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(

    @field:SerializedName("ingredientId")
    val ingredientId: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("unit")
    val unit: String,

    @field:SerializedName("amount")
    val amount: Int,

    @field:SerializedName("ingredient")
    val ingredient: String,

    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("recipeId")
    val recipeId: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,

    @field:SerializedName("products")
    val products: Product
) : Parcelable
