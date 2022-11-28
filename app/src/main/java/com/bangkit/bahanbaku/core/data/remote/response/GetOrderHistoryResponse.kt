package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetOrderHistoryResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: List<OrderHistoryItem>
)

data class ProductHistoryItem(

	@field:SerializedName("productImage")
	val productImage: String,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("stock")
	val stock: Int,
)

data class OrderHistoryItem(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("recipe")
	val recipe: Recipe,

	@field:SerializedName("paymentLink")
	val paymentLink: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("directPayId")
	val directPayId: String,

	@field:SerializedName("addressId")
	val addressId: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("products")
	val products: List<ProductHistoryItem>
)

data class Recipe(

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
)
