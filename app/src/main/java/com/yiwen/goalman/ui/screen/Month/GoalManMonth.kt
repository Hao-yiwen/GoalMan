package com.yiwen.goalman.ui.screen.Month

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import com.yiwen.goalman.ui.screen.Components.TopBar

@Composable
fun GoalManMonth(openDrawerState: () -> Unit){
    Scaffold(
        topBar = {
            TopBar(navigationClick = { openDrawerState() }) {
                Text(text = "GoalManMonth")
            }
        }
    ) {
        Text(text = "GoalManMonth", modifier = Modifier.padding(it))
    }
}