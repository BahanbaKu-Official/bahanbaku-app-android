package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val image: String,
    val author: String,
    val rating: Double,
    val description: String,
    val title: String,
    val steps: List<String>,
//	val tags: List<String>,
    val createdAt: String,
    val times: Int,
    val servings: Int,
    val ingredients: List<String>,
    val totalViews: Int,
    val id: String,
    val updatedAt: String
) : Parcelable
