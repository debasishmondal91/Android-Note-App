package com.example.mvvmroomtutorial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var desc: String,
    var createdOn: Date?,
    var mCompleted: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}