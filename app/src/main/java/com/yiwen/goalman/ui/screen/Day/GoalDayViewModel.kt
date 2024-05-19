package com.yiwen.goalman.ui.screen.Day

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yiwen.goalman.Enum.GoalStatus
import com.yiwen.goalman.Enum.Level
import com.yiwen.goalman.GOAL_PERCENTAGE
import com.yiwen.goalman.GoalApplication
import com.yiwen.goalman.data.CompletionRecordsRepository
import com.yiwen.goalman.data.GoalRepository
import com.yiwen.goalman.data.GoalSettingRepository
import com.yiwen.goalman.model.CompletionRecord
import com.yiwen.goalman.model.Goal
import com.yiwen.goalman.utils.RecordComplianceRate
import com.yiwen.goalman.utils.getNowDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

class GoalDayViewModel(
    val goalRepositoryProvider: GoalRepository,
    val goalSettingRepository: GoalSettingRepository,
    val completionRecordsReposityProvider: CompletionRecordsRepository
) : ViewModel() {
    val _uiState = MutableStateFlow(GoalDayUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // 获取目标列表
            processGoalList()
            // 获取已打卡天数
            getPositiveDays()
            // 获取热力图数据
            getHeatMapCalendarData()
        }
    }

    fun processGoalList() {
        viewModelScope.launch {
            val gl = goalRepositoryProvider.getAllPositiveGoals().toMutableStateList()

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
        val newGoal = Goal(UUID.randomUUID().toString(), description, 1, getNowDate(), getNowDate())
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

    fun getHeatMapCalendarData(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val startDate = _uiState.value.startDate
            val endDate = _uiState.value.endDate
            val results = mutableMapOf<LocalDate, Level>()
            val records = completionRecordsReposityProvider.getCompletionRecordsByDateRange(
                getNowDate(startDate),
                getNowDate(endDate)
            ).groupBy { it.completionTime }

            // 假设 records 是 Map<LocalDate, List<Record>>
            (0..ChronoUnit.DAYS.between(startDate, endDate)).forEach { count ->
                val date = startDate.plusDays(count)
                val dailyRecords = records.get(getNowDate(date)) ?: emptyList()

                if (dailyRecords.isEmpty()) {
                    results[date] = Level.Zero
                } else {
                    val recordComplianceRate = RecordComplianceRate(dailyRecords)
                    val levelIndex = Math.floor(recordComplianceRate * 4).toInt()
                    results[date] = Level.values()[levelIndex]
                }
            }

            _uiState.value = _uiState.value.copy(
                date = results
            )
        }
    }


    fun checkIn() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("GoalListViewModel", _uiState.value.goals.toString())
            // 获取今天的目标
            val currentGoals = goalRepositoryProvider.getGoalsByUpdateTime(getNowDate());
            val positiveGoals = currentGoals.filter { it.status != GoalStatus.UNAVAILABLE.value }
            val negativeGoals = currentGoals.filter { it.status == GoalStatus.UNAVAILABLE.value }
            if (currentGoals.size > 0) {
                // 判断是否达到打卡条件，当前目标完成状态为 2 的目标数量大于总目标数量的 60% 时，可以进行打卡
                val positivieGoals =
                    currentGoals.filter { it.status == GoalStatus.COMPLETED.value }?.size!! >= Math.ceil(
                        positiveGoals.size * GOAL_PERCENTAGE
                    )
                // 判断今天是否已经打过卡了
                val todayCheckIn =
                    completionRecordsReposityProvider.getCompletionRecordByCompletionTime(
                        getNowDate()
                    )
                if (positivieGoals) {
                    val completionRecords = positiveGoals.map {
                        CompletionRecord(
                            id = UUID.randomUUID().toString(),
                            goalId = it.id,
                            completionTime = getNowDate(),
                            status = it.status
                        )
                    }
                    if (todayCheckIn.size > 0) {
                        // 更新打卡内容
                        coroutineScope {
                            // 更新有意义的打卡记录
                            completionRecords.forEach {
                                val index =
                                    todayCheckIn.indexOfFirst { record -> record.goalId == it.goalId }
                                if (index >= 0) {
                                    // 如果存在则更新
                                    launch {
                                        completionRecordsReposityProvider.update(
                                            it.copy(
                                                id = todayCheckIn[index].id
                                            )
                                        )
                                    }
                                } else {
                                    // 否则就插入
                                    launch {
                                        completionRecordsReposityProvider.insert(it)
                                    }
                                }
                            }
                            // 删除无意义的打卡记录
                            negativeGoals.forEach {
                                val index =
                                    todayCheckIn.indexOfFirst { record -> record.goalId == it.id }
                                if (index >= 0) {
                                    launch {
                                        completionRecordsReposityProvider.delete(todayCheckIn[index])
                                    }
                                }
                            }
                        }
                        getHeatMapCalendarData()
                        _uiState.value = _uiState.value.copy(
                            snackbarHostState = _uiState.value.snackbarHostState.apply {
                                showSnackbar("更新打卡成功~")
                            }
                        )
                    } else {
                        completionRecordsReposityProvider.insertAll(*completionRecords.toTypedArray())
                        getHeatMapCalendarData()
                        _uiState.value = _uiState.value.copy(
                            snackbarHostState = _uiState.value.snackbarHostState.apply {
                                showSnackbar("打卡成功~")
                            }
                        )
                    }
                } else if (!positivieGoals) {
                    _uiState.value = _uiState.value.copy(
                        snackbarHostState = _uiState.value.snackbarHostState.apply {
                            showSnackbar("未完成当日目标的${GOAL_PERCENTAGE * 100}%以上,无法完成打卡")
                        }
                    )
                }
            } else {
                Log.d("GoalListViewModel", "currentGoals:" + currentGoals.toString())
                _uiState.value = _uiState.value.copy(
                    snackbarHostState = _uiState.value.snackbarHostState.apply {
                        showSnackbar("还没有设置目标哦~")
                    }
                )
            }
        }
    }


    fun getPositiveDays() {
        viewModelScope.launch(Dispatchers.IO) {
            val positiveDays = completionRecordsReposityProvider.getTotalPositiveCompletionRecords()
            _uiState.value = _uiState.value.copy(
                positiveDays = positiveDays
            )
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GoalApplication)
                GoalDayViewModel(
                    application.container.goalRepository,
                    application.container.workManagerRepository,
                    application.container.completionRecordsRepository
                )
            }
        }
    }
}