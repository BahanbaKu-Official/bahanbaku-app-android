package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAddressByUser(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: List<AddressResultItem>
) : Parcelable

@Parcelize
data class AddressResultItem(

	@field:SerializedName("zipCode")
	val zipCode: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("street")
	val street: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("district")
	val district: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("addressId")
	val addressId: String,

	@field:SerializedName("longitude")
	val longitude: Double,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable
