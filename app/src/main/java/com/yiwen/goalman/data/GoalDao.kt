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

    @Delete
    fun delete(goal: Goal): Unit

    @Update
    fun update(goal: Goal): Unit

    @Query("SELECT * FROM goal")
    fun getAllGoalsFlow(): Flow<List<Goal>>

    @Query("SELECT * FROM goal")
    suspend fun getAllGoals(): List<Goal>

    @Query("SELECT * FROM goal WHERE id = :id")
    fun getGoalByIdFlow(id: Int): Flow<Goal>

    @Query("SELECT * FROM goal WHERE id = :id")
    suspend fun getGoalById(id: Int): Goal
}