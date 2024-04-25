package com.yiwen.goalman.data

import androidx.room.Query
import com.yiwen.goalman.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun insertGoal(goal: Goal)

    fun deleteGoal(id: String)

    fun updateGoal(goal: Goal)

    fun getAllGoalsFlow(): Flow<List<Goal>>

    fun getGoalByIdFlow(id: Int): Flow<Goal>

    suspend fun getAllGoals(): List<Goal>

    suspend fun getAllPositiveGoals(): List<Goal>

    fun getAllPositiveGoalsFlow(): Flow<List<Goal>>

    fun getGoalsByUpdateTimeFlow(date: String): Flow<List<Goal>>

    fun getGoalsByUpdateTime(date: String): List<Goal>

    suspend fun getGoalById(id: Int): Goal

    suspend fun updateStatus(): Unit
}

class GoalRepositoryProvider(val goalDao: GoalDao) : GoalRepository {
    override fun insertGoal(goal: Goal) {
        goalDao.insert(goal)
    }

    override fun deleteGoal(id: String) {
        goalDao.delete(id)
    }

    override fun updateGoal(goal: Goal) {
        goalDao.update(goal)
    }

    override fun getAllGoalsFlow(): Flow<List<Goal>> {
        return goalDao.getAllGoalsFlow()
    }

    override fun getGoalByIdFlow(id: Int): Flow<Goal> {
        return goalDao.getGoalByIdFlow(id)
    }

    override suspend fun getAllGoals(): List<Goal> {
        return goalDao.getAllGoals()
    }

    override suspend fun getAllPositiveGoals(): List<Goal> {
        return goalDao.getAllPositiveGoals()
    }

    override fun getAllPositiveGoalsFlow(): Flow<List<Goal>> {
        return goalDao.getAllPositiveGoalsFlow()
    }

    override fun getGoalsByUpdateTimeFlow(date: String): Flow<List<Goal>> {
        return goalDao.getGoalsByUpdateTimeFlow(date)
    }

    override fun getGoalsByUpdateTime(date: String): List<Goal> {
        return goalDao.getGoalsByUpdateTime(date)
    }

    override suspend fun getGoalById(id: Int): Goal {
        return goalDao.getGoalById(id)
    }

    override suspend fun updateStatus() {
        goalDao.updateStatus()
    }
}