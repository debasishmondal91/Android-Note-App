package com.example.mvvmroomtutorial.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mvvmroomtutorial.data.local.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    fun insertNote(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): MutableList<Note>

    @Query("DELETE FROM notes_table WHERE id = :id")
    fun deleteNote(id: Int)
}