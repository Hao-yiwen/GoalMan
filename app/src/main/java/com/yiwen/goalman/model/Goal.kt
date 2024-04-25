package com.yiwen.goalman.model

import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yiwen.goalman.utils.getNowDate
import org.jetbrains.annotations.NotNull
import java.util.UUID

/**
 * @param status
 * 1: in progress
 * 2: completed
 * @description Goal表将会存储所有过期和未过期的目标，status 为 1 表示目标正在进行中，status 为 2 表示目标已经完成
 *     status为3则为删除，之所以不直接删除是为了在完成情况记录表中进行数据分析
 */

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @NotNull val description: String,
    @NotNull val status: Int,
    @NotNull val createdTime: String = getNowDate(),
    @NotNull val updatedTime: String = getNowDate()
)

