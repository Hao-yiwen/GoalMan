package com.yiwen.goalman.ui.screen.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    drawerState: DrawerState,
    selectedItem: DrawerItem? = null,
    onSelected: (DrawerItem) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DRAWERITEMS.forEach {
                NavigationDrawerItem(label = {
                    Text(text = it.name, style = MaterialTheme.typography.bodyLarge)
                }, selected = it == selectedItem, onClick = {
                    onSelected(it)
                }, icon = {
                    Image(
                        painter = painterResource(id = it.painter),
                        contentDescription = it.name,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .size(24.dp)
                    )
                })
            }
        }
    }
}