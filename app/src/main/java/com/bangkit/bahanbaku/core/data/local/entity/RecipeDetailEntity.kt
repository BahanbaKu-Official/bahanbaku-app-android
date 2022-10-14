package com.bangkit.bahanbaku.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe_detail")
data class RecipeDetailEntity(

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("portion")
    val portion: Int,

    @field:SerializedName("rating")
    val rating: Int,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("steps")
    val steps: List<Step>,

    @PrimaryKey
    @field:SerializedName("recipeId")
    val recipeId: String,

    @field:SerializedName("tags")
    val tags: List<String>,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("deletedAt")
    val deletedAt: String,

//	@field:SerializedName("reviews")
//	val reviews: List<Any>,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("ingredients")
    val ingredients: List<IngredientEntity>,

    @field:SerializedName("time")
    val time: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable