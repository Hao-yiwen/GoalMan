package com.yiwen.goalman.ui.screen

import androidx.compose.material3.SnackbarHostState
import com.yiwen.goalman.Enum.Level
import com.yiwen.goalman.model.Goal
import java.time.LocalDate

data class GoalManUiState(
    val goals: List<Goal> = emptyList(),
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val date: MutableMap<LocalDate, Level> = mutableMapOf(),
    val startDate: LocalDate = LocalDate.now().minusMonths(6),
    val endDate: LocalDate = LocalDate.now(),
    val positiveDays: Int = 0,
)