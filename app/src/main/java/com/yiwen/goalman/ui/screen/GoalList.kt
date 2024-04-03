package com.yiwen.goalman.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.yiwen.goalman.R
import com.yiwen.goalman.model.Goal


@Composable
fun GoalList(
    modifier: Modifier,
    goals: List<Goal>,
    updateGoal: (goal: Goal) -> Unit,
    deleteGoal: (goal: Goal) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentGoal by remember {
        mutableStateOf(Goal(0, "", 0))
    }

    Column(modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.process),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                )
                LazyColumn {
                    items(goals) { goal ->
                        if (goal.status == 1) {
                            GoalItem(Modifier, goal, updateGoal) { it, goal ->
                                showDialog = it
                                currentGoal = goal
                            }
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.complete),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                )
                LazyColumn {
                    items(goals) { goal ->
                        if (goal.status == 2) {
                            GoalItem(Modifier, goal, updateGoal) { it, goal ->
                                showDialog = it
                                currentGoal = goal
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = { Text("删除目标") },
            text = { Text("确定要删除这个目标吗？") },
            confirmButton = {
                Button(
                    onClick = {
                        // 调用删除目标的逻辑
                        showDialog = false
                        deleteGoal(currentGoal)
                    }
                ) {
                    Text("确认")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
fun GoalItem(
    modifier: Modifier,
    goal: Goal,
    updateGoal: (goal: Goal) -> Unit,
    updateShowDialog: (showDialog: Boolean, goal: Goal) -> Unit
) {
    val longPressModifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onLongPress = {
            updateShowDialog(true, goal)
        })
    }

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 使用IconButton和Icon来创建圆形的“复选框”
        Checkbox(
            checked = goal.status == 2, onCheckedChange = { checked ->
                updateGoal(goal.copy(status = if (checked) 2 else 1))
            },
            // 使用colors参数自定义选中和未选中的颜色
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary, // 选中时的颜色
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // 未选中时的颜色
                checkmarkColor = Color.White // 对勾的颜色
            ),
            // 可以使用modifier来调整大小，但Checkbox的形状始终是圆的
            modifier = Modifier
        )
        Text(
            text = goal.description,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    updateGoal(goal.copy(status = goal.status))
                }
                .then(longPressModifier),
            style = MaterialTheme.typography.displayMedium,
            textDecoration = if (goal.status == 2) TextDecoration.LineThrough else null
        )
    }
}