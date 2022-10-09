package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddBookmarkResponse(

	@field:SerializedName("status")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: AddBookmarkResults
)

data class AddBookmarkResults(
	val any: Any? = null
)
