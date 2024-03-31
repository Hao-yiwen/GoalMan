package com.yiwen.goalman.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.yiwen.goalman.model.Goal

@Composable
fun GoalList(modifier: Modifier, goals: List<Goal>, viewModel: GoalListViewModel) {
    LazyColumn(modifier = modifier) {
        items(goals) { goal ->
            GoalItem(Modifier, goal, viewModel)
        }
    }
}

@Composable
fun GoalItem(modifier: Modifier, goal: Goal, viewModel: GoalListViewModel) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 使用IconButton和Icon来创建圆形的“复选框”
        Checkbox(
            checked = goal.isCompleted,
            onCheckedChange = { checked ->
                viewModel.updateGoal(goal.copy(isCompleted = checked))
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
            text = goal.description, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    viewModel.updateGoal(goal.copy(isCompleted = !goal.isCompleted))
                },
            style = MaterialTheme.typography.displayMedium,
            textDecoration = if (goal.isCompleted) TextDecoration.LineThrough else null
        )
    }
}