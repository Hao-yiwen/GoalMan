package com.yiwen.goalman.data

import android.content.Context

interface AppContainer {
    val goalRepository: GoalRepository
}

class AppDataContainer(val context: Context) : AppContainer {
    override val goalRepository: GoalRepository by lazy {
        GoalRepositoryProvider(GoalDatabase.getDatabase(context).goalDao())
    }
}