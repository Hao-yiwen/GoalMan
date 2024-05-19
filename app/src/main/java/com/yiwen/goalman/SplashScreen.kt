package com.yiwen.goalman

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // 使用 rememberUpdatedState 保持最新的 onTimeout 回调
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    // 使用 LaunchedEffect 启动一个协程并延迟一段时间后调用 onTimeout 回调
    LaunchedEffect(Unit) {
        delay(300) // 延迟 300 秒
        currentOnTimeout()
    }


    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(bottom = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = "目标达人",
                color = Color.Black,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}