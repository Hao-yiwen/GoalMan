package com.yiwen.goalman.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yiwen.goalman.model.CompletionRecord
import kotlinx.coroutines.flow.Flow
import java.sql.Date

@Dao
interface CompletionRecordsDao {
    @Insert
    fun insert(completionRecord: CompletionRecord): Unit

    @Insert
    fun insertAll(vararg completionRecords: List<CompletionRecord>): Unit

    @Delete
    fun delete(completionRecord: CompletionRecord): Unit

    @Delete
    fun deleteAll(vararg completionRecords: List<CompletionRecord>): Unit

    @Update
    fun update(completionRecord: CompletionRecord): Unit

    @Query("SELECT * FROM completion_records")
    fun getAllCompletionRecordsOfFlow(): Flow<List<CompletionRecord>>

    @Query("SELECT * FROM completion_records")
    suspend fun getAllCompletionRecords(): List<CompletionRecord>

    @Query("SELECT * FROM completion_records WHERE completionTime = :completionTime")
    fun getCompletionRecordByCompletionTimeFlow(completionTime: Date): Flow<List<CompletionRecord>>

    @Query("SELECT * FROM completion_records WHERE completionTime = :completionTime")
    suspend fun getCompletionRecordByCompletionTime(completionTime: Date): List<CompletionRecord>

    @Query("SELECT * FROM completion_records WHERE goalId = :goalId")
    fun getCompletionRecordByGoalIdFlow(goalId: String): Flow<List<CompletionRecord>>

    @Query("SELECT * FROM completion_records WHERE goalId = :goalId")
    suspend fun getCompletionRecordByGoalId(goalId: String): List<CompletionRecord>
}
