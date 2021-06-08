package com.steamtofu.mejaku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Classes::class],version = 1)
abstract class ClassesRoomDatabase : RoomDatabase() {

    abstract fun classesDao(): ClassesDao
    companion object {
        @Volatile
        private var INSTANCE: ClassesRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): ClassesRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ClassesRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ClassesRoomDatabase::class.java, "class_database")
                        .build()
                }
            }
            return INSTANCE as ClassesRoomDatabase
        }
    }
}