package com.aubrey.recepku.data.common

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromListString(list: List<String?>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toListString(value: String?): List<String?>? {
        return value?.split(",")?.map { it.trim() }
    }
}