package com.yiwen.goalman.ui.screen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yiwen.goalman.BuildConfig
import com.yiwen.goalman.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigationClick: () -> Unit,
    actionScope: @Composable RowScope.() -> Unit = {},
    titleScope: @Composable () -> Unit
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Box(
                modifier = Modifier.background(Color.Transparent)
            ) {
                IconButton(onClick = {
                    navigationClick()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "菜单",
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        },
        title = titleScope,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
        actions = actionScope
    )
}