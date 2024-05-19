package com.yiwen.goalman.ui.screen.Week

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import com.yiwen.goalman.ui.screen.Components.TopBar

@Composable
fun GoalManWeek(openDrawerState: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(navigationClick = { openDrawerState() }) {
                Text(text = "GoalManWeek")
            }
        }
    ) {
        Text(text = "GoalManWeek", modifier = Modifier.padding(it))
    }
}