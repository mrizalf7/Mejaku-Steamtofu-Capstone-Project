package com.steamtofu.mejamu.ui.classes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.steamtofu.mejamu.database.Classes
import com.steamtofu.mejamu.repository.ClassesRepository

class CreateClassViewModel(application: Application): ViewModel() {

    private val mClassesRepository: ClassesRepository = ClassesRepository(application)
    fun insert(classes: Classes) {
        mClassesRepository.insert(classes)
    }
    fun update(classes: Classes) {
        mClassesRepository.update(classes)
    }
    fun delete(classes: Classes) {
        mClassesRepository.delete(classes)
    }
}