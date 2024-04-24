package com.yiwen.goalman.ui.screen

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yiwen.goalman.Enum.GoalStatus
import com.yiwen.goalman.GOAL_PERCENTAGE
import com.yiwen.goalman.GoalApplication
import com.yiwen.goalman.data.CompletionRecordsRepository
import com.yiwen.goalman.data.CompletionRecordsReposityProvider
import com.yiwen.goalman.data.GoalRepository
import com.yiwen.goalman.data.GoalRepositoryProvider
import com.yiwen.goalman.data.GoalSettingRepository
import com.yiwen.goalman.model.CompletionRecord
import com.yiwen.goalman.model.Goal
import com.yiwen.goalman.work.requestPermissons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.UUID

class GoalListViewModel(
    val goalRepositoryProvider: GoalRepository,
    val goalSettingRepository: GoalSettingRepository,
    val completionRecordsReposityProvider: CompletionRecordsRepository
) : ViewModel() {
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

    fun reSettingGoal() {
        viewModelScope.launch {
            goalSettingRepository.reSettingGoal()
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

    fun deleteGoal(goal: Goal) {
        val currentGoals = _uiState.value.goals
        val index = currentGoals.indexOfFirst { it.id == goal.id }
        val newGoals = currentGoals.toMutableList()
        newGoals[index] = newGoals[index].copy(status = 3)
        _uiState.value = _uiState.value.copy(
            goals = newGoals
        )
        viewModelScope.launch(Dispatchers.IO) {
            goalRepositoryProvider.deleteGoal(goal.id)
        }
    }

    fun checkIn() {
        viewModelScope.launch {
            Log.d("GoalListViewModel", _uiState.value.goals.toString())
            val currentGoals = _uiState.value.goals
            if (currentGoals.size > 0) {
                // 判断是否达到打卡条件，当前目标完成状态为 2 的目标数量大于总目标数量的 60% 时，可以进行打卡
                val positivieGoals =
                    currentGoals.filter { it.status == GoalStatus.COMPLETED.value }?.size!! >= Math.ceil(
                        currentGoals.size * GOAL_PERCENTAGE
                    )
                // 判断今天是否已经打过卡了
                val todayCheckIn =
                    completionRecordsReposityProvider.getCompletionRecordByCompletionTime(
                        Date(System.currentTimeMillis())
                    )
                if (positivieGoals && todayCheckIn.isEmpty()) {
                    val completionRecords = currentGoals.map {
                        CompletionRecord(
                            id = UUID.randomUUID().toString(),
                            goalId = it.id,
                            completionTime = Date(System.currentTimeMillis())
                        )
                    }
                    completionRecordsReposityProvider.insertAll(completionRecords)
                } else if (!positivieGoals) {
                    _uiState.value = _uiState.value.copy(
                        snackbarHostState = _uiState.value.snackbarHostState.apply {
                            showSnackbar("未完成当日目标的60%以上,无法完成打卡")
                        }
                    )
                } else if (todayCheckIn.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        snackbarHostState = _uiState.value.snackbarHostState.apply {
                            showSnackbar("今日已经打过卡了")
                        }
                    )
                }
            }
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GoalApplication)
                GoalListViewModel(
                    application.container.goalRepository,
                    application.container.workManagerRepository,
                    application.container.completionRecordsRepository
                )
            }
        }
    }
}