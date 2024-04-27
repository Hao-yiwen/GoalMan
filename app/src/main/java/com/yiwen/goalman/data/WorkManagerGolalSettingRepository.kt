package com.yiwen.goalman.data

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yiwen.goalman.work.ReSettingGoalWorker
import com.yiwen.goalman.work.requestPermissons
import java.util.Calendar
import java.util.concurrent.TimeUnit

class WorkManagerGolalSettingRepository(context: Context) : GoalSettingRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun reSettingGoal() {

        val now = Calendar.getInstance()

        val nextMidnight = now.clone() as Calendar

        // 设置时间为明天的午夜12点
        nextMidnight.add(Calendar.DAY_OF_YEAR, 1) // 加一天
        nextMidnight.set(Calendar.HOUR_OF_DAY, 0) // 设置小时为0 (24小时制的午夜12点)
        nextMidnight.set(Calendar.MINUTE, 0) // 设置分钟为0
        nextMidnight.set(Calendar.SECOND, 0) // 设置秒为0
        nextMidnight.set(Calendar.MILLISECOND, 0) // 设置毫秒为0

        // 计算时间差
        val initialDelay = nextMidnight.timeInMillis - now.timeInMillis

        // 转换时间差为合适的单位，这里以毫秒为例
        val initialDelayInSeconds = TimeUnit.MILLISECONDS.toSeconds(initialDelay)

        val scheduleBuilder = PeriodicWorkRequestBuilder<ReSettingGoalWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelayInSeconds, TimeUnit.SECONDS)
            .build()

        // 使用唯一的名称来识别这个周期性工作
        val uniqueWorkName = "resettingGoalWork"

        val enqueueUniquePeriodicWork = workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            scheduleBuilder
        )
        // @todo
        // 如果重置成功 此事用户正在使用app 需要弹窗提示刷新应用

    }
}
