package com.abdurrahmanjun.androidcase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdurrahmanjun.androidcase.data.database.model.Favorite

@Database(entities = [Favorite::class ], version = 1)
abstract class AndroidCaseDatabase: RoomDatabase() {

    abstract val androidCaseDao: AndroidCaseDao

    companion object {
        @Volatile
        private var INSTANCE: AndroidCaseDatabase? = null

        fun getInstance(context: Context): AndroidCaseDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AndroidCaseDatabase::class.java,
                    "school_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}