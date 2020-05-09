package com.example.mvvmroomtutorial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmroomtutorial.data.local.dao.NoteDao
import com.example.mvvmroomtutorial.data.local.entity.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object FACTORY {
        private var instance: NoteDatabase? = null
        fun getInMemoryDatabase(context: Context): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
            }

            return instance
        }
    }

    fun destroyInstance(){
        instance = null
    }

}