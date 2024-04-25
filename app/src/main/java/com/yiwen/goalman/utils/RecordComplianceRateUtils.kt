package com.yiwen.goalman.utils

import com.yiwen.goalman.Enum.GoalStatus
import com.yiwen.goalman.Enum.Level
import com.yiwen.goalman.model.CompletionRecord
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun RecordComplianceRate(records: List<CompletionRecord>): Double {
    val total = records.size
    val positive = records.filter { it.status == GoalStatus.COMPLETED.value }.size
    // 保留两位小数
    return ((positive * 1.0) / total).let { String.format("%.2f", it).toDouble() }
}
