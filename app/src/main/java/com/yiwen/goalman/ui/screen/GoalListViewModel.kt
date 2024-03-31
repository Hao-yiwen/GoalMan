package com.yiwen.goalman.ui.screen

import androidx.lifecycle.ViewModel
import com.yiwen.goalman.model.Goal
import kotlinx.coroutines.flow.MutableStateFlow

class GoalListViewModel : ViewModel() {
    val _uiState = MutableStateFlow(GoalListUiState())

    val uiState = _uiState

    fun addGoal(description: String) {
        val currentGoals = _uiState.value.goals
        val newGoal = Goal(currentGoals.size + 1, description, false)
        _uiState.value = _uiState.value.copy(
            goals = currentGoals + newGoal
        )
    }

    fun updateGoal(goal: Goal) {
        val currentGoals = _uiState.value.goals
        val index = currentGoals.indexOfFirst { it.id == goal.id }
        val newGoals = currentGoals.toMutableList()
        newGoals[index] = goal
        _uiState.value = _uiState.value.copy(
            goals = newGoals
        )
    }
}