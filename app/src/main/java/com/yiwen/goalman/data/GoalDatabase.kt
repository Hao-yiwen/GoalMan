package com.yiwen.goalman.data

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.yiwen.goalman.model.Goal


@Database(entities = [Goal::class], version = 1, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao

    companion object{
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