package com.yiwen.goalman.ui.screen.Day

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kizitonwose.calendar.compose.HeatMapCalendar
import com.kizitonwose.calendar.compose.heatmapcalendar.rememberHeatMapCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import com.yiwen.goalman.Enum.Level
import com.yiwen.goalman.ui.screen.BottomContent
import com.yiwen.goalman.ui.screen.Calendar.Day
import com.yiwen.goalman.ui.screen.Calendar.MonthHeader
import com.yiwen.goalman.ui.screen.Calendar.WeekHeader
import com.yiwen.goalman.work.requestPermissons
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalManDay(
    viewModel: GoalDayViewModel = viewModel(factory = GoalDayViewModel.factory),
    openDrawerState: () -> Unit
) {
    val goalUiState by viewModel.uiState.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    // @description: 协成作用域
    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }
    var newGoalDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    val appContext = context.applicationContext

    // calendar test
    var refreshKey by remember { mutableIntStateOf(1) }
//    val goalUiState.endDate = remember { LocalDate.now() }
//    // 展示最近6个月的数据
//    val goalUiState.startDate = remember { goalUiState.endDate.minusMonths(6) }
//    val data = remember { mutableStateOf<Map<LocalDate, Level>>(emptyMap()) }
    var selection by remember { mutableStateOf<Pair<LocalDate, Level>?>(null) }

    LaunchedEffect(Unit) {
        requestPermissons(context = appContext, activityContext = context)
        viewModel.reSettingGoal()
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = goalUiState.snackbarHostState)
    }, topBar = {
        GoalManDayTopBar(viewModel, goalUiState, openDrawerState = {
            openDrawerState()
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showBottomSheet = true
            }, modifier = Modifier.padding(30.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }) {
        Column {
            val state = rememberHeatMapCalendarState(
                startMonth = goalUiState.startDate.yearMonth,
                endMonth = goalUiState.endDate.yearMonth,
                firstVisibleMonth = goalUiState.endDate.yearMonth,
                firstDayOfWeek = firstDayOfWeekFromLocale(),
            )
            Text(
                text = "目标完成情况", modifier = Modifier
                    .padding(
                        start = 20.dp,
                        top = it.calculateTopPadding(),
                        end = 20.dp,
                        bottom = 10.dp
                    )
                    .padding(top = 10.dp), style = MaterialTheme.typography.displayMedium
            )
            HeatMapCalendar(
                modifier = Modifier.padding(horizontal = 10.dp),
                state = state,
                contentPadding = PaddingValues(end = 6.dp),
                dayContent = { day, week ->
                    Day(
                        day = day,
                        startDate = goalUiState.startDate,
                        endDate = goalUiState.endDate,
                        week = week,
                        level = goalUiState.date[day.date] ?: Level.Zero,
                    ) { clicked ->
                        selection = Pair(clicked, goalUiState.date[clicked] ?: Level.Zero)
                    }
                },
                weekHeader = { WeekHeader(it) },
                monthHeader = { MonthHeader(it, goalUiState.endDate, state) },
            )
            GoalList(
                modifier = Modifier.padding(vertical = 10.dp),
                goalUiState.goals,
                viewModel::updateGoal,
                viewModel::deleteGoal,
            )
            Box(modifier = Modifier.weight(1f)) {
                BottomContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.BottomCenter),
                    selection = selection,
                ) { refreshKey += 1 }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    }, sheetState = sheetState
                ) {
                    TextField(
                        value = newGoalDescription,
                        onValueChange = { newGoalDescription = it },
                        label = {
                            Text(text = "目标描述")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Button(
                        enabled = newGoalDescription.isNotBlank(), onClick = {
                            // 添加新目标逻辑
                            if (newGoalDescription.isNotBlank()) {
                                viewModel.addGoal(newGoalDescription)
                                newGoalDescription = "" // 重置输入框
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    showBottomSheet = false
                                }
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(bottom = 30.dp)
                    ) {
                        Text("添加目标")
                    }
                }
            }
        }
    }
}

