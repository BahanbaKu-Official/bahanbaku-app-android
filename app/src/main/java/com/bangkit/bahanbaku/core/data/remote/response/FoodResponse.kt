package com.bangkit.bahanbaku.core.data.remote.response

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class FoodResponse(
    val list: List<FoodEntity>
)

data class FoodEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String
)
