package com.yiwen.goalman.model

import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * @param status
 * 1: in progress
 * 2: completed
 */

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NotNull val description: String,
    @NotNull val status: Int
)

