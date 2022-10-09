package com.bangkit.bahanbaku.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile")
data class ProfileEntity(

    @field:SerializedName("bookmark")
    val bookmarks: List<String>,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("picture")
    val picture: String,

    @field:SerializedName("username")
    val username: String
)