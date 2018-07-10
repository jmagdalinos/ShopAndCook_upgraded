package com.johnmagdalinos.android.shopandcook2.data

import android.arch.persistence.room.TypeConverter

/**
 * Type converters used in the database
 */
class Converters {
    @TypeConverter
    fun booleanFromInt(checked: Int) : Boolean = checked == 1

    @TypeConverter
    fun intFromBoolean(checked: Boolean) : Int = if (checked) 1 else 0
}