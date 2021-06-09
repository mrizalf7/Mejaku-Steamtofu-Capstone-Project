package com.steamtofu.mejamu.repository

import android.app.Application
import androidx.paging.DataSource
import com.steamtofu.mejamu.database.Classes
import com.steamtofu.mejamu.database.ClassesDao
import com.steamtofu.mejamu.database.ClassesRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ClassesRepository(application: Application) {
    private val mClassesDao: ClassesDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = ClassesRoomDatabase.getDatabase(application)
        mClassesDao = db.classesDao()
    }
    fun getAllClasses(): DataSource.Factory<Int,Classes> = mClassesDao.getAllClasses()
    fun insert(classes: Classes) {
        executorService.execute { mClassesDao.insert(classes) }
    }
    fun delete(classes: Classes) {
        executorService.execute { mClassesDao.delete(classes) }
    }
    fun update(classes: Classes) {
        executorService.execute { mClassesDao.update(classes) }
    }
}