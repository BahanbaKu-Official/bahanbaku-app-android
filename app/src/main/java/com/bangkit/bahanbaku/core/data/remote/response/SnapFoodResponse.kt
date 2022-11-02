package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SnapFoodResponse(

	@field:SerializedName("internationalFood")
	val internationalFood: List<SnapFoodItem>,

	@field:SerializedName("traditionalFood")
	val traditionalFood: List<SnapFoodItem>
)

data class SnapFoodItem(

	@field:SerializedName("image_link")
	val imageLink: String,

	@field:SerializedName("probability")
	val probability: String,

	@field:SerializedName("food")
	val food: String
)
