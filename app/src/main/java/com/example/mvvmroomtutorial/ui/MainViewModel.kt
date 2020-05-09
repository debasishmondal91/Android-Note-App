package com.example.mvvmroomtutorial.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvmroomtutorial.data.local.NoteDatabase
import com.example.mvvmroomtutorial.data.local.entity.Note
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var noteDB: NoteDatabase? = null
    private var asyncTaskGetAllTask: AsyncTask<Void, Void, MutableList<Note>>? = null
    private var mNavigator: WeakReference<MainNavigator>? = null
    private var mutableLiveData: MutableLiveData<MutableList<Note>>? = null
    var mutabledataUpdate: MutableLiveData<Note>? = null

    init {
        noteDB = NoteDatabase.getInMemoryDatabase(application)
        mutableLiveData = MutableLiveData()
    }

    private fun getNavigator(): MainNavigator? {
        return mNavigator!!.get()
    }

    fun setNavigator(navigator: MainNavigator) {
        this.mNavigator = WeakReference(navigator)
    }

    @SuppressLint("CheckResult")
    fun getAllNotes(): MutableLiveData<MutableList<Note>>? {
        asyncTaskGetAllTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, MutableList<Note>>() {
            override fun doInBackground(vararg params: Void?): MutableList<Note> {
                return noteDB!!.noteDao().getAllNotes()
            }

            override fun onPostExecute(result: MutableList<Note>?) {
                super.onPostExecute(result)
                if (result!!.isNotEmpty()) {
                    mutableLiveData!!.value = result
                }

            }

        }

        asyncTaskGetAllTask!!.execute()
        return mutableLiveData
    }

    @SuppressLint("CheckResult")
    fun saveNoteToDB(
        note: String
    ) {
        val note1 = Note(note, "", Date(), false)
        mutabledataUpdate = MutableLiveData()
        Completable.fromAction {
            noteDB?.noteDao()!!.insertNote(note1)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutabledataUpdate!!.value = note1
                //updateUi()
                getNavigator()!!.onInsertTaskSuccess()
                Log.d("Room", "Blog Db: list insertion was successful")
            }, {
                Log.d("Room", "Blog Db: list insertion wasn't successful")
            })

    }

    @SuppressLint("CheckResult")
    fun deleteNote(id: Int?) {
        Completable.fromAction {
            noteDB!!.noteDao().deleteNote(id!!)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //getNavigator()!!.onInsertTaskSuccess()
                getAllNotes()
                Log.d("Room", "Blog Db: list Delete was successful")
            }, {
                Log.d("Room", "Blog Db: list Delete wasn't successful")
            })
    }

    fun updateUi(): MutableLiveData<Note>? {
        return mutabledataUpdate
    }


}