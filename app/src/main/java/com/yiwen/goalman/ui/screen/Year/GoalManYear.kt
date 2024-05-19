package com.yiwen.goalman.ui.screen.Year

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import com.yiwen.goalman.ui.screen.Components.TopBar

@Composable
fun GoalManYear(openDrawerState: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(navigationClick = { openDrawerState() }) {
                Text(text = "GoalManYear")
            }
        }
    ) {
        Text(text = "GoalManYear", modifier = Modifier.padding(it))
    }
}