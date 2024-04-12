package com.yiwen.goalman.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yiwen.goalman.R
import com.yiwen.goalman.data.GoalDatabase

class ReSettingGoalWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val applicationContext = applicationContext
            val database = GoalDatabase.getDatabase(applicationContext).goalDao()

            database.updateStatus()

            makePlantReminderNotification(
                applicationContext.resources.getString(R.string.reset),
                applicationContext
            )

            return Result.success()
        } catch (e: Exception) {
            makePlantReminderNotification(
                "重置数据库失败。",
                applicationContext
            )
            return Result.failure()
        }
    }
}