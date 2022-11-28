package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetPaymentMethodResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: List<PaymentItem>
)

data class PaymentItem(

	@field:SerializedName("paymentImage")
	val paymentImage: String,

	@field:SerializedName("paymentCode")
	val paymentCode: String,

	@field:SerializedName("paymentCategory")
	val paymentCategory: String,

	@field:SerializedName("paymentId")
	val paymentId: Int,

	@field:SerializedName("paymentName")
	val paymentName: String
)
