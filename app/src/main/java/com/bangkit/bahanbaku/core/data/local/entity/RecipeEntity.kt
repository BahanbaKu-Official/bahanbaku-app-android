package com.bangkit.bahanbaku.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class RecipeEntity(
    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("steps")
    val steps: List<String>,
//
//	@field:SerializedName("tags")
//	val tags: List<String>,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("times")
    val times: Int,

    @field:SerializedName("servings")
    val servings: Int,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("totalViews")
    val totalViews: Int,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
