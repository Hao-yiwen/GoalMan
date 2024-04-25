package com.yiwen.goalman.utils

import androidx.room.TypeConverter
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @deprecated
 * 直接使用字符串存储日期，使用转换器反而不方便
 */
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
    println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()))
}