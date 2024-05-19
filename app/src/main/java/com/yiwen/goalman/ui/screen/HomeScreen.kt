package com.yiwen.goalman.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yiwen.goalman.R

import com.yiwen.goalman.ui.screen.Day.GoalManDay
import com.yiwen.goalman.ui.screen.Month.GoalManMonth
import com.yiwen.goalman.ui.screen.Week.GoalManWeek
import com.yiwen.goalman.ui.screen.Year.GoalManYear
import com.yiwen.goalman.ui.screen.navigation.DRAWERITEMS
import com.yiwen.goalman.ui.screen.navigation.DrawerContent
import com.yiwen.goalman.ui.screen.navigation.DrawerItem
import com.yiwen.goalman.work.requestPermissons
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val homeState = viewModel.uiState.collectAsState()
    // @description: 协成作用域
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val selectedDrawerItem: MutableState<DrawerItem> = remember { mutableStateOf(DRAWERITEMS[0]) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        DrawerContent(
            drawerState = drawerState,
            selectedItem = selectedDrawerItem.value,
            onSelected = {
                scope.launch {
                    drawerState.close()
                    selectedDrawerItem.value = it
                }
            })
    }) {
        when (selectedDrawerItem.value) {
            DRAWERITEMS[0] -> {
                GoalManDay(
                    viewModel,
                    openDrawerState = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            DRAWERITEMS[1] -> {
                GoalManWeek(
                    openDrawerState = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            DRAWERITEMS[2] -> {
                GoalManMonth(
                    openDrawerState = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            DRAWERITEMS[3] -> {
                GoalManYear(
                    openDrawerState = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            else -> {
                GoalManDay(viewModel, openDrawerState = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            }
        }
    }

    if (homeState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 1f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier.size(100.dp)
                )
                Text(text = stringResource(id = R.string.loading))
            }
        }
    }
}



