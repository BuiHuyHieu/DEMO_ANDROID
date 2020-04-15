package com.example.app_daily.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_daily.Class.Note
import com.example.app_daily.Interface.OnDeleteItemListener
import com.example.app_daily.Interface.OnEditItemListener
import com.example.app_daily.R
import kotlinx.android.synthetic.main.row_noteapp.view.*

public class RecyclerViewAdapter(var context: Context,var list:List<Note>):
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    lateinit var mOnDeleteItemListener:OnDeleteItemListener
    lateinit var mOnEditItemListener: OnEditItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val View = LayoutInflater.from(parent.context).inflate(R.layout.row_noteapp,parent,false)
        return MyViewHolder(View)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.setData(item,position)
    }
    inner class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var currentPosition:Int=0
        lateinit var currentNote:Note
        init {
            itemView.bt_delete_note.setOnClickListener{

                object:AlertDialog.Builder(context){}.setMessage("Do you want delete?")
                    .setTitle("Delete?").setPositiveButton("Yes"
                    ) { dialog, which ->
                        mOnDeleteItemListener = context as OnDeleteItemListener
                        com.example.app_daily.model.Display.fullScreen(context as Activity)
                        mOnDeleteItemListener.sendInput(currentNote)
                    }
                    .setNegativeButton("No") {
                            dialog,which->
                        com.example.app_daily.model.Display.fullScreen(context as Activity)

                    }.show()
            }
            itemView.bt_edit_note.setOnClickListener {
                mOnEditItemListener = context as OnEditItemListener
                mOnEditItemListener.sendNote(currentNote)
            }
        }
        fun setData(note:Note,position: Int)
        {
            itemView.tv_TitleNote.setText(note.title)
            itemView.tv_TimeNote.setText(note.time)
            itemView.tv_PlaceNote.setText(note.place)
            itemView.tv_DescriptionNote.setText(note.description)
            this.currentPosition=position
            this.currentNote= note
        }
    }
}