package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetRecipesByTagResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: RecipeByTagResults
)

data class RecipeTags(

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("recipeId")
	val recipeId: String
)

data class RecipeByTagResults(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("recipes")
	val recipes: List<RecipeItem>,

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
