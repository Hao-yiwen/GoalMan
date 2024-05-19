package com.yiwen.goalman.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yiwen.goalman.BuildConfig
import com.yiwen.goalman.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    viewModel: GoalManViewModel, goalUiState: GoalManUiState, openDrawerState: () -> Unit
) {
    val context = LocalContext.current
    val appContext = context.applicationContext

    CenterAlignedTopAppBar(navigationIcon = {
        Box(
            modifier = Modifier.background(Color.Transparent)
        ) {
            IconButton(onClick = {
                openDrawerState()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "菜单",
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (BuildConfig.DEBUG) {
                    Text(
                        text = stringResource(
                            id = R.string.title_name, goalUiState.positiveDays
                        ) + "(DEBUG)",
                        style = MaterialTheme.typography.displayMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.title_name, goalUiState.positiveDays),
                        style = MaterialTheme.typography.displayLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
        actions = {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.check_in),
                    contentDescription = "打卡",
                    modifier = Modifier
                        .width(34.dp)
                        .height(34.dp)
                        .clickable {
                            viewModel.checkIn()
                        })
            }
        })
}