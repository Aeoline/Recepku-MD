package com.aubrey.recepku.data.common

import androidx.room.TypeConverter
import com.google.gson.Gson

class StringListConverter {
    @TypeConverter
    fun fromListString(list: List<String?>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toListString(value: String?): List<String?>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}