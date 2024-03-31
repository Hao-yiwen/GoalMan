package com.yiwen.goalman.ui.screen

import com.yiwen.goalman.model.Goal

data class GoalListUiState(
    val goals: List<Goal> = emptyList()
)