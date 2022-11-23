package com.bangkit.bahanbaku.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product")
data class ProductEntity(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("deletedAt")
    val deletedAt: String,

    @PrimaryKey
    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("stock")
    val stock: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String,

    @field:SerializedName("productImage")
    val productImage: String
) : Parcelable
