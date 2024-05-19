package com.yiwen.goalman.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
fun HomeScreen() {
    // @description: 协成作用域
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val selectedDrawerItem: MutableState<DrawerItem> = remember { mutableStateOf(DRAWERITEMS[0]) }

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
                GoalManDay(openDrawerState = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            }
        }
    }
}



