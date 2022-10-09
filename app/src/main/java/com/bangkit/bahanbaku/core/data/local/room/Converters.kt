package com.bangkit.bahanbaku.core.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun jsonToStrList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun strListToJson(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}