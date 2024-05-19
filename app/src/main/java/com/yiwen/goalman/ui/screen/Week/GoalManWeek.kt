package com.yiwen.goalman.ui.screen.Week

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.wear.compose.material.Text
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.shape.Shape
import com.yiwen.goalman.R
import com.yiwen.goalman.ui.screen.Components.TopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlinx.coroutines.delay

@Composable
fun GoalManWeek(openDrawerState: () -> Unit) {
    val modelProducer = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            modelProducer.tryRunTransaction {
                columnSeries {
                    repeat(3) {
                        series(
                            List(Defaults.ENTRY_COUNT) {
                                Defaults.COLUMN_LAYER_MIN_Y +
                                        Random.nextFloat() * Defaults.COLUMN_LAYER_RELATIVE_MAX_Y
                            },
                        )
                    }
                }
            }
        }
    }

    Scaffold(topBar = {
        TopBar(navigationClick = { openDrawerState() }) {
            Text(
                text = stringResource(id = R.string.wekky_goal),
                style = MaterialTheme.typography.displayLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
    }) {
        CartesianChartHost(
            chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    columnProvider =
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(
                            color = color1,
                            thickness = 10.dp,
                            shape =
                            Shape.rounded(
                                bottomLeftPercent = 40,
                                bottomRightPercent = 40,
                            ),
                        ),
                        rememberLineComponent(
                            color = color2,
                            thickness = 10.dp,
                        ),
                        rememberLineComponent(
                            color = color3,
                            thickness = 10.dp,
                            shape =
                            Shape.rounded(
                                topLeftPercent = 40,
                                topRightPercent = 40,
                            ),
                        ),
                    ),
                    mergeMode = { ColumnCartesianLayer.MergeMode.Stacked },
                ),
                startAxis =
                rememberStartAxis(
                    itemPlacer = startAxisItemPlacer,
                    labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES,
                ),
                bottomAxis = rememberBottomAxis(labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES),
            ),
            modelProducer = modelProducer,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            // @todo 触摸时候显示坐标
            marker = null,
            runInitialAnimation = false,
            zoomState = rememberVicoZoomState(zoomEnabled = false),
        )
    }
}

private val color1 = Color(0xff6438a7)
private val color2 = Color(0xff3490de)
private val color3 = Color(0xff73e8dc)
private val startAxisItemPlacer = AxisItemPlacer.Vertical.count({ 3 })
private const val AXIS_LABEL_ROTATION_DEGREES = 0f

object Defaults {
    const val ENTRY_COUNT = 8
    const val MAX_Y = 20
    const val COLUMN_LAYER_MIN_Y = 2
    const val COLUMN_LAYER_RELATIVE_MAX_Y = MAX_Y - COLUMN_LAYER_MIN_Y
}
