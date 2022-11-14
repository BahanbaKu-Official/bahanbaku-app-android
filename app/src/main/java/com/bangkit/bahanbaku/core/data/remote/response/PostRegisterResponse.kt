package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostRegisterResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("results")
	val results: RegisterResult
) : Parcelable

@Parcelize
data class RegisterResult(

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("passwordChangedAt")
	val passwordChangedAt: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("isVerified")
	val isVerified: Boolean,

	@field:SerializedName("profileImage")
	val profileImage: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("emailVerificationToken")
	val emailVerificationToken: String
) : Parcelable
