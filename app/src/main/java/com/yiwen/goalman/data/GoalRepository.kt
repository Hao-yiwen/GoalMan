package com.yiwen.goalman.data

import com.yiwen.goalman.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun insertGoal(goal: Goal)

    fun deleteGoal(goal: Goal)

    fun updateGoal(goal: Goal)

    fun getAllGoalsFlow(): Flow<List<Goal>>

    fun getGoalByIdFlow(id: Int): Flow<Goal>

    suspend fun getAllGoals(): List<Goal>

    suspend fun getGoalById(id: Int): Goal
}

class GoalRepositoryProvider(val goalDao: GoalDao) : GoalRepository {
    override fun insertGoal(goal: Goal) {
        goalDao.insert(goal)
    }

    override fun deleteGoal(goal: Goal) {
        goalDao.delete(goal)
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

    override suspend fun getGoalById(id: Int): Goal {
        return goalDao.getGoalById(id)
    }
}