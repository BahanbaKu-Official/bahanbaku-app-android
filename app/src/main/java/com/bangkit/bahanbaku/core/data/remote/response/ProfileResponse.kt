package com.bangkit.bahanbaku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: ProfileItem
)

data class ProfileItem(

	@field:SerializedName("bookmark")
	val bookmarks: List<String>,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("username")
	val username: String
)

data class ShippingItem(

	@field:SerializedName("cost")
	val cost: Int,

	@field:SerializedName("distance")
	val distance: Int,

	@field:SerializedName("id")
	val id: String
)

data class Origin(

	@field:SerializedName("lng")
	val lng: Double,

	@field:SerializedName("lat")
	val lat: Double
)
