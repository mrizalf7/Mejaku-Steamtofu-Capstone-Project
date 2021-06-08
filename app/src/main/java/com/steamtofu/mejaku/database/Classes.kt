package com.steamtofu.mejaku.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Classes (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "class")
    var className: String? = null,
    @ColumnInfo(name = "file")
    var file : String? = null,
    @ColumnInfo(name = "date")
    var date: String? = null

): Parcelable

