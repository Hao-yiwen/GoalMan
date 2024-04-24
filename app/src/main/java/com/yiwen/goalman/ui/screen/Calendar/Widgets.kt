package com.yiwen.goalman.ui.screen.Calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.heatmapcalendar.HeatMapCalendarState
import com.kizitonwose.calendar.compose.heatmapcalendar.HeatMapWeek
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.yiwen.goalman.DAYSIZE
import com.yiwen.goalman.Enum.Level
import com.yiwen.goalman.utils.displayText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


@Composable
fun MonthHeader(
    calendarMonth: CalendarMonth,
    endDate: LocalDate,
    state: HeatMapCalendarState,
) {
    val density = LocalDensity.current
    val firstFullyVisibleMonth by remember {
        // Find the first index with at most one box out of bounds.
        derivedStateOf { getMonthWithYear(state.layoutInfo, DAYSIZE, density) }
    }
    if (calendarMonth.weekDays.first().first().date <= endDate) {
        val month = calendarMonth.yearMonth
        val title = if (month == firstFullyVisibleMonth) {
            month.displayText(short = true)
        } else {
            month.month.displayText()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp, start = 2.dp),
        ) {
            Text(text = title, fontSize = 10.sp)
        }
    }
}

fun getMonthWithYear(
    layoutInfo: CalendarLayoutInfo,
    DAYSIZE: Dp,
    density: Density,
): YearMonth? {
    val visibleItemsInfo = layoutInfo.visibleMonthsInfo
    return when {
        visibleItemsInfo.isEmpty() -> null
        visibleItemsInfo.count() == 1 -> visibleItemsInfo.first().month.yearMonth
        else -> {
            val firstItem = visibleItemsInfo.first()
            val daySizePx = with(density) { DAYSIZE.toPx() }
            if (
                firstItem.size < daySizePx * 3 || // Ensure the Month + Year text can fit.
                firstItem.offset < layoutInfo.viewportStartOffset && // Ensure the week row size - 1 is visible.
                (layoutInfo.viewportStartOffset - firstItem.offset > daySizePx)
            ) {
                visibleItemsInfo[1].month.yearMonth
            } else {
                firstItem.month.yearMonth
            }
        }
    }
}

@Composable
fun WeekHeader(dayOfWeek: DayOfWeek) {
    Box(
        modifier = Modifier
            .height(DAYSIZE) // Must set a height on the day of week so it aligns with the day.
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = dayOfWeek.displayText(),
            modifier = Modifier.align(Alignment.Center),
            fontSize = 10.sp,
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    startDate: LocalDate,
    endDate: LocalDate,
    week: HeatMapWeek,
    level: Level,
    onClick: (LocalDate) -> Unit,
) {
    val weekDates = week.days.map { it.date }
    if (day.date in startDate..endDate) {
        LevelBox(level.color) { onClick(day.date) }
    } else if (weekDates.contains(startDate)) {
        LevelBox(Color.Transparent)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}

@Composable
fun LevelBox(color: Color, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .size(DAYSIZE) // Must set a size on the day.
            .padding(2.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color = color)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
    )
}
