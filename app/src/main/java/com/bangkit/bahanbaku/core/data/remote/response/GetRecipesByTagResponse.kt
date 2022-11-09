package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetRecipesByTagResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: Results
) : Parcelable

@Parcelize
data class Results(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("recipes")
	val recipes: List<RecipeItem>,

	@field:SerializedName("deletedAt")
	val deletedAt: String,

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class RecipeTags(

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("recipeId")
	val recipeId: String
) : Parcelable
