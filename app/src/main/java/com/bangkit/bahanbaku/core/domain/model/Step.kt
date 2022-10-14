package com.bangkit.bahanbaku.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val sequence: Int,
    val createdAt: String,
    val stepId: String,
    val ingredients: List<Ingredient>,
    val step: String,
    val recipeId: String,
    val updatedAt: String
) : Parcelable