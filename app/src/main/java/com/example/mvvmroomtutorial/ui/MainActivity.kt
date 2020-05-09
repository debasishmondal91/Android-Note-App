package com.example.mvvmroomtutorial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmroomtutorial.R
import com.example.mvvmroomtutorial.data.local.NoteDatabase
import com.example.mvvmroomtutorial.data.local.entity.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainNavigator {

    private var mMainViewModel: MainViewModel? = null
    var recyclerAdapter: RecyclerViewAdapter? = null
    private var noteDB: NoteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        noteDB = NoteDatabase.getInMemoryDatabase(this)

        add.setOnClickListener {
            val note = edit_text.text.toString().trim()
            mMainViewModel!!.saveNoteToDB(note)
        }

        initUI()
    }

    private fun initUI() {
        mMainViewModel!!.setNavigator(this)

        mMainViewModel!!.getAllNotes()!!.observe(this,
            Observer<MutableList<Note>> { t ->
                recyclerAdapter = RecyclerViewAdapter(t, mMainViewModel!!)
                recycler_view?.adapter = recyclerAdapter
                recycler_view?.layoutManager = LinearLayoutManager(this@MainActivity)
            })
    }

    override fun onInsertTaskSuccess() {
        mMainViewModel!!.updateUi()!!.observe(this, Observer {
            edit_text.clearFocus()
            edit_text.setText("")
            recyclerAdapter!!.addItem(it)
        })

    }
}
