package com.yiwen.goalman.utils

import androidx.room.TypeConverter
import java.sql.Date

class SqliteConverts {
    @TypeConverter
    fun dateToLong(date: java.sql.Date): Long {
        return date.time
    }

    @TypeConverter
    fun longToDate(time: Long): java.sql.Date {
        return java.sql.Date(time)
    }
}

fun main() {
    println(Date(System.currentTimeMillis()))
    println(Date(System.currentTimeMillis()).time)

    println(Date(Date(System.currentTimeMillis()).time))
}