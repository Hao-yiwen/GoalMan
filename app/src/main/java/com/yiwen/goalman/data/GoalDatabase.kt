package com.yiwen.goalman.data

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.yiwen.goalman.model.CompletionRecord
import com.yiwen.goalman.model.Goal
import com.yiwen.goalman.utils.SqliteConverts


@Database(entities = [Goal::class, CompletionRecord::class], version = 3, exportSchema = false)
@TypeConverters(SqliteConverts::class)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao

    abstract fun completionRecordsDao(): CompletionRecordsDao

    companion object {
        @Volatile
        private var Instance: GoalDatabase? = null

        fun getDatabase(context: Context): GoalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GoalDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}