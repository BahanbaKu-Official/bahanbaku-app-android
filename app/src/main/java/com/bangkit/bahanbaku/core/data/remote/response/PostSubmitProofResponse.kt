package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostSubmitProofResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
