package com.example.mvvmroomtutorial.ui

import android.content.Context
import com.example.mvvmroomtutorial.data.local.entity.Note

interface MainNavigator {
    //fun getContext(): Context
    //fun onGetAllTaskSuccess(notes: MutableList<Note>?)
    fun onInsertTaskSuccess()
}