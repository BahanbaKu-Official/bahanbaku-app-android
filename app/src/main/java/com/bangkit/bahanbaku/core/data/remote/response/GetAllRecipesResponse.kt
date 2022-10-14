package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllRecipesResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: List<RecipeItem>
) : Parcelable

@Parcelize
data class RecipeItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

//	@field:SerializedName("deletedAt")
//	val deletedAt: String,

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

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable
