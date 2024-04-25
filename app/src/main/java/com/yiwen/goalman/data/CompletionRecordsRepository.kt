package com.yiwen.goalman.data

import com.yiwen.goalman.model.CompletionRecord
import kotlinx.coroutines.flow.Flow

interface CompletionRecordsRepository {
    fun insert(completionRecord: CompletionRecord): Unit

    fun insertAll(vararg completionRecords: CompletionRecord): Unit

    fun delete(completionRecord: CompletionRecord): Unit

    fun deleteAll(vararg completionRecords: CompletionRecord): Unit

    fun update(completionRecord: CompletionRecord): Unit

    fun getAllCompletionRecordsOfFlow(): Flow<List<CompletionRecord>>

    suspend fun getAllCompletionRecords(): List<CompletionRecord>

    fun getCompletionRecordByCompletionTimeFlow(completionTime: String): Flow<List<CompletionRecord>>

    suspend fun getCompletionRecordByCompletionTime(completionTime: String): List<CompletionRecord>

    fun getCompletionRecordByGoalIdFlow(goalId: String): Flow<List<CompletionRecord>>

    suspend fun getCompletionRecordByGoalId(
        goalId:
        String
    ): List<CompletionRecord>

    suspend fun getCompletionRecordsByDateRange(
        startDate: String,
        endDate: String
    ): List<CompletionRecord>
}

class CompletionRecordsReposityProvider(val completionRecordsDao: CompletionRecordsDao) :
    CompletionRecordsRepository {
    override fun insert(completionRecord: CompletionRecord) {
        completionRecordsDao.insert(completionRecord)
    }

    override fun insertAll(vararg completionRecords: CompletionRecord) {
        completionRecordsDao.insertAll(*completionRecords)
    }

    override fun delete(completionRecord: CompletionRecord) {
        completionRecordsDao.delete(completionRecord)
    }

    override fun deleteAll(vararg completionRecords: CompletionRecord) {
        completionRecordsDao.deleteAll(*completionRecords)
    }

    override fun update(completionRecord: CompletionRecord) {
        completionRecordsDao.update(completionRecord)
    }

    override fun getAllCompletionRecordsOfFlow(): Flow<List<CompletionRecord>> {
        return completionRecordsDao.getAllCompletionRecordsOfFlow()
    }

    override suspend fun getAllCompletionRecords(): List<CompletionRecord> {
        return completionRecordsDao.getAllCompletionRecords()
    }

    override fun getCompletionRecordByCompletionTimeFlow(completionTime: String): Flow<List<CompletionRecord>> {
        return completionRecordsDao.getCompletionRecordByCompletionTimeFlow(completionTime)
    }

    override suspend fun getCompletionRecordByCompletionTime(completionTime: String): List<CompletionRecord> {
        return completionRecordsDao.getCompletionRecordByCompletionTime(completionTime)
    }

    override fun getCompletionRecordByGoalIdFlow(goalId: String): Flow<List<CompletionRecord>> {
        return completionRecordsDao.getCompletionRecordByGoalIdFlow(goalId)
    }

    override suspend fun getCompletionRecordByGoalId(goalId: String): List<CompletionRecord> {
        return completionRecordsDao.getCompletionRecordByGoalId(goalId)
    }

    override suspend fun getCompletionRecordsByDateRange(
        startDate: String,
        endDate: String
    ): List<CompletionRecord> {
        return completionRecordsDao.getCompletionRecordsByDateRange(startDate, endDate)
    }
}
