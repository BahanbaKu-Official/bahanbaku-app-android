package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostCreateDirectPaymentResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: DirectPaymentResults
)

data class DirectPaymentResults(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("paymentLink")
	val paymentLink: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("recipeId")
	val recipeId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("directPayId")
	val directPayId: String,

	@field:SerializedName("status")
	val status: String
)
