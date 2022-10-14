package com.bangkit.bahanbaku.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class RecipeEntity(
    @field:SerializedName("createdAt")
    val createdAt: String,

//    @field:SerializedName("deletedAt")
//    val deletedAt: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("portion")
    val portion: Int,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("time")
    val time: Int,

    @field:SerializedName("title")
    val title: String,

    @PrimaryKey
    @field:SerializedName("recipeId")
    val recipeId: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
