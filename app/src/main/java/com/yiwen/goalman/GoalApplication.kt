package com.yiwen.goalman

import android.app.Application
import com.yiwen.goalman.data.AppContainer
import com.yiwen.goalman.data.AppDataContainer

class GoalApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}