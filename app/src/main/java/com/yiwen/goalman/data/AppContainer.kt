package com.yiwen.goalman.data

import android.content.Context

interface AppContainer {
    val goalRepository: GoalRepository

    val workManagerRepository: GoalSettingRepository

    val completionRecordsRepository: CompletionRecordsRepository
}

class AppDataContainer(val context: Context) : AppContainer {
    override val goalRepository: GoalRepository by lazy {
        GoalRepositoryProvider(GoalDatabase.getDatabase(context).goalDao())
    }

    override val workManagerRepository: GoalSettingRepository by lazy {
        WorkManagerGolalSettingRepository(context)
    }

    override val completionRecordsRepository: CompletionRecordsRepository by lazy {
        CompletionRecordsReposityProvider(
            GoalDatabase.getDatabase(context).completionRecordsDao()
        )
    }


}