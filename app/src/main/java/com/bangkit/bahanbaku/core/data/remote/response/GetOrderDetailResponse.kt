package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetOrderDetailResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: DirectPaymentDetailResult
)

data class ProductDirectPays(

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("directPayId")
	val directPayId: String
)

data class OrderRecipe(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: Any,

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
)

data class DirectPaymentDetailResult(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("address")
	val address: Address,

	@field:SerializedName("recipe")
	val recipe: OrderRecipe,

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

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("products")
	val products: List<PaymentProductsItem>
)

data class PaymentProductsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("deletedAt")
	val deletedAt: Any,

	@field:SerializedName("productImage")
	val productImage: String,

	@field:SerializedName("product_directPays")
	val productDirectPays: ProductDirectPays,

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
)

data class Address(

	@field:SerializedName("zipCode")
	val zipCode: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("receiverName")
	val receiverName: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("receiverPhoneNumber")
	val receiverPhoneNumber: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("addressId")
	val addressId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("isPrimary")
	val isPrimary: Boolean,

	@field:SerializedName("street")
	val street: String,

	@field:SerializedName("district")
	val district: String,

	@field:SerializedName("longitude")
	val longitude: Double,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
