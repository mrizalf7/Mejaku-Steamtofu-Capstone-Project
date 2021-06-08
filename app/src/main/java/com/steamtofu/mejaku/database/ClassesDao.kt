package com.steamtofu.mejaku.database


import androidx.paging.DataSource
import androidx.room.*

@Dao
interface ClassesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(classes: Classes)
    @Update
    fun update(classes: Classes)
    @Delete
    fun delete(classes: Classes)
    @Query("SELECT * from classes ORDER BY id ASC")
    fun getAllClasses(): DataSource.Factory<Int, Classes>
}