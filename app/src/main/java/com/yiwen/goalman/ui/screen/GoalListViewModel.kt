package com.yiwen.goalman.ui.screen

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yiwen.goalman.GoalApplication
import com.yiwen.goalman.data.GoalRepository
import com.yiwen.goalman.data.GoalRepositoryProvider
import com.yiwen.goalman.model.Goal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GoalListViewModel(val goalRepositoryProvider: GoalRepository) : ViewModel() {
    val _uiState = MutableStateFlow(GoalListUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            processGoalList()
        }
    }

    fun processGoalList() {
        viewModelScope.launch {
            val gl = goalRepositoryProvider.getAllGoals().toMutableStateList()

            _uiState.value = _uiState.value.copy(
                goals = gl
            )
        }
    }

    fun addGoal(description: String) {
        val currentGoals = _uiState.value.goals
        // 第三个参数 status 为 1 表示目标正在进行中
        val newGoal = Goal(UUID.randomUUID().toString(), description, 1)
        _uiState.value = _uiState.value.copy(
            goals = currentGoals + newGoal
        )
        viewModelScope.launch(Dispatchers.IO) {
            goalRepositoryProvider.insertGoal(newGoal)
        }
    }


    fun updateGoal(goal: Goal) {
        val currentGoals = _uiState.value.goals
        val index = currentGoals.indexOfFirst { it.id == goal.id }
        val newGoals = currentGoals.toMutableList()
        newGoals[index] = goal
        _uiState.value = _uiState.value.copy(
            goals = newGoals
        )
        viewModelScope.launch(Dispatchers.IO) {
            goalRepositoryProvider.updateGoal(newGoals[index])
        }
    }

    fun deleteGoal(goal: Goal){
        val currentGoals = _uiState.value.goals
        val index = currentGoals.indexOfFirst { it.id == goal.id }
        val newGoals = currentGoals.toMutableList()
        newGoals.removeAt(index)
        _uiState.value = _uiState.value.copy(
            goals = newGoals
        )
        viewModelScope.launch(Dispatchers.IO) {
            goalRepositoryProvider.deleteGoal(goal)
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GoalApplication)
                GoalListViewModel(application.container.goalRepository)
            }
        }
    }
}