package com.yiwen.goalman.ui.screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yiwen.goalman.R

interface DrawerItem {
    val name: String
    val painter: Int
}

data class DrawerItemImpl(
    override val name: String,
    override val painter: Int
) : DrawerItem

val DRAWERITEMS = listOf(
    DrawerItemImpl(
        "每日计划",
        R.drawable.day
    ),
    DrawerItemImpl(
        "每周计划",
        R.drawable.week
    ),
    DrawerItemImpl(
        "每月计划",
        R.drawable.month
    ),
    DrawerItemImpl(
        "每年计划",
        R.drawable.year
    )
)