package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetRecipeByIdResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: RecipeDetailItem
) : Parcelable

@Parcelize
data class StepsItem(

	@field:SerializedName("sequence")
	val sequence: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("stepId")
	val stepId: String,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("step")
	val step: String,

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class IngredientsItem(

	@field:SerializedName("ingredientId")
	val ingredientId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("amount")
	val amount: Double,

	@field:SerializedName("ingredient")
	val ingredient: String,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("products")
	val products: Products
) : Parcelable

@Parcelize
data class Products(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: String,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class RecipeDetailItem(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("steps")
	val steps: List<StepsItem>,

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("tags")
	val tags: List<RecipeTagsResponse>,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: String,

//	@field:SerializedName("reviews")
//	val reviews: List<Any>,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("time")
	val time: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable
