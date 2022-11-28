package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetDirectPaymentInfoResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: PaymentInfoResults
)

data class PaymentInfoResults(

	@field:SerializedName("bankAccount")
	val bankAccount: String,

	@field:SerializedName("bankName")
	val bankName: String,

	@field:SerializedName("bankOwner")
	val bankOwner: String
)
