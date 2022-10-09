package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateLocationResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: UpdateLocationResults
)

data class UpdateLocationResults(
	val any: Any? = null
)
