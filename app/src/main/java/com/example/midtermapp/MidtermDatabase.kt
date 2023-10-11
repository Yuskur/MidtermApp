package com.example.midtermapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class MidtermDatabase : RoomDatabase() {

    abstract val midtermDao: MidtermDao
    //in the case that the database is not already created it will make one

    companion object {
        @Volatile
        private var INSTANCE: MidtermDatabase? = null

        fun getDatabase(context: Context): MidtermDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MidtermDatabase::class.java,
                        "midterm_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}