package com.yiwen.goalman.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.yiwen.goalman.utils.SqliteConverts
import org.jetbrains.annotations.NotNull
import java.sql.Date
import java.util.UUID

@Entity(
    tableName = "completion_records", foreignKeys = [
        ForeignKey(
            entity = Goal::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CompletionRecord(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @NotNull val goalId: String,
    // 这块需要存储记录是哪一天
    @NotNull val completionTime: String,
    // @description 1表示未完成 2表示完成
    @NotNull val status: Int,
    // 记录这是那一周
)

fun main() {
    // 2024-04-24
    println(Date(System.currentTimeMillis()))
}