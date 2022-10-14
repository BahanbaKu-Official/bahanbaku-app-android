package com.bangkit.bahanbaku.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "step")
data class Step(

    @field:SerializedName("sequence")
    val sequence: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @PrimaryKey
    @field:SerializedName("stepId")
    val stepId: String,

    @field:SerializedName("ingredients")
    val ingredients: List<IngredientEntity>,

    @field:SerializedName("step")
    val step: String,

    @field:SerializedName("recipeId")
    val recipeId: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable
