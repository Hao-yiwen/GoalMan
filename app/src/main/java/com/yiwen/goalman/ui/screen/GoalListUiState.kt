package com.yiwen.goalman.ui.screen

import androidx.compose.material3.SnackbarHostState
import com.yiwen.goalman.model.Goal

data class GoalListUiState(
    val goals: List<Goal> = emptyList(),
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)