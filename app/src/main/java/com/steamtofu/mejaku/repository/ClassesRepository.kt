package com.steamtofu.mejaku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.steamtofu.mejaku.database.Classes
import com.steamtofu.mejaku.database.ClassesDao
import com.steamtofu.mejaku.database.ClassesRoomDatabase
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