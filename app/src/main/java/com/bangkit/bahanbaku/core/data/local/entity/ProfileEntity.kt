package com.bangkit.bahanbaku.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile")
data class ProfileEntity(

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("isVerified")
    val isVerified: Boolean,

    @field:SerializedName("profileImage")
    val profileImage: String,

    @PrimaryKey
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)