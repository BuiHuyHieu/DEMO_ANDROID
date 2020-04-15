package com.example.app_daily.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.app_daily.Class.Note
import com.example.app_daily.Database.DatabaseNote
import com.example.app_daily.R
import kotlinx.android.synthetic.main.add_note.view.*

public class fragment_addnote:DialogFragment(){
    public interface connect  {
        fun input()
    }
    var mConnect:connect?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_note,container,false)
        view.bt_submit_add.setOnClickListener {
            val title = view.edt_action_note.text.toString().trim()
            val time = "Time: " + view.edt_time_note.text.toString().trim()
            val place = "Place: " + view.edt_place_note.text.toString().trim()
            val description = "Description: " + view.edt_description_note.text.toString().trim()
            val database = DatabaseNote(dialog!!.context)
            database.insertData(title,time,place,description)
            mConnect!!.input()
            dialog?.dismiss()
        }
        view.bt_cancel_add.setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog=  super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        val params = WindowManager.LayoutParams()
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        dialog!!.window!!.attributes = params
        super.onStart()
    }

    override fun onAttach(context: Context) {
        mConnect =  context as connect

        super.onAttach(context)
    }

}