package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetail(
    val author: String,
    val portion: Int,
    val rating: Int,
    val description: String,
    val title: String,
    val steps: List<Step>,
    val recipeId: String,
    val tags: List<String>,
    val createdAt: String,
    val deletedAt: String,
//	@field:SerializedName("reviews")
//	val reviews: List<Any>,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val time: Int,
    val updatedAt: String
) : Parcelable
