package com.example.mvvmroomtutorial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmroomtutorial.R
import com.example.mvvmroomtutorial.data.local.entity.Note

/**
 * Created by debu on 15/03/20.
 */
class RecyclerViewAdapter(
    note: MutableList<Note>?,
    private val mMainViewModel: MainViewModel
) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>() {

    private val tasks: MutableList<Note>? = note

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val task = tasks?.get(position)
        holder.name?.text = task?.title
        holder.creationDateView!!.text = task?.createdOn.toString()
        holder.rlMain.setOnLongClickListener {
            mMainViewModel.deleteNote(task?.id)
            //notifyDataSetChanged()
            return@setOnLongClickListener true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 0
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = itemView.findViewById(R.id.name) as TextView
        var creationDateView: TextView? = itemView.findViewById(R.id.creation_date) as TextView
        var rlMain: RelativeLayout = itemView.findViewById(R.id.rl_main)
    }

    fun addItem(note: Note) {
        tasks?.add(note)
        notifyItemInserted(itemCount - 1)
    }
}