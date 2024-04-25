package com.yiwen.goalman.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yiwen.goalman.model.Goal
import kotlinx.coroutines.flow.Flow


@Dao
interface GoalDao {
    @Insert
    fun insert(goal: Goal): Unit

    @Query("UPDATE goal SET status = 3 WHERE id = :id")
    fun delete(id: String): Unit

    @Update
    fun update(goal: Goal): Unit

    @Query("SELECT * FROM goal")
    fun getAllGoalsFlow(): Flow<List<Goal>>

    @Query("SELECT * FROM goal")
    suspend fun getAllGoals(): List<Goal>

    @Query("SELECT * FROM goal WHERE status != 3")
    suspend fun getAllPositiveGoals(): List<Goal>

    @Query("SELECT * FROM goal WHERE status != 3")
    fun getAllPositiveGoalsFlow(): Flow<List<Goal>> {
        return getAllGoalsFlow()
    }

    @Query("SELECT * FROM goal WHERE id = :id")
    fun getGoalByIdFlow(id: Int): Flow<Goal>

    @Query("SELECT * FROM goal WHERE updatedTime = :date")
    fun getGoalsByUpdateTimeFlow(date: String): Flow<List<Goal>>

    @Query("SELECT * FROM goal WHERE updatedTime = :date")
    fun getGoalsByUpdateTime(date: String): List<Goal>

    @Query("SELECT * FROM goal WHERE id = :id")
    suspend fun getGoalById(id: Int): Goal

    @Query("UPDATE goal SET status = 1 WHERE status = 2")
    suspend fun updateStatus()
}