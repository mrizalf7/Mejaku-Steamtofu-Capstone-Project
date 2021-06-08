package com.steamtofu.mejaku.helper

import androidx.recyclerview.widget.DiffUtil
import com.steamtofu.mejaku.database.Classes

class ClassesDiffCallback(private val mOldClassesList: List<Classes>, private val mNewClassesList: List<Classes>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldClassesList.size
    }
    override fun getNewListSize(): Int {
        return mNewClassesList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldClassesList[oldItemPosition].id == mNewClassesList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldClassesList[oldItemPosition]
        val newEmployee = mNewClassesList[newItemPosition]
        return oldEmployee.className == newEmployee.className && oldEmployee.date == newEmployee.date
    }
}