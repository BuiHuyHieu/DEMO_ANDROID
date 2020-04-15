package com.example.app_daily.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.app_daily.Class.Note
import com.example.app_daily.R
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.edit_note.view.*

public class fragment_editnote:DialogFragment(){
    public interface connectEdit  {
        fun inputEdit(note: Note)
    }
    var mConnect:connectEdit?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_note,container,false)
        view.bt_submit_edit.setOnClickListener {
            val title = view.edt_action_edit.text.toString().trim()
            val time = "Time: " + view.edt_time_edit.text.toString().trim()
            val place = "Place: " + view.edt_place_edit.text.toString().trim()
            val description = "Description: " + view.edt_description_edit.text.toString().trim()
            mConnect?.inputEdit(Note(null,title,time,place,description))
            dialog?.dismiss()
        }
        view.bt_cancel_edit.setOnClickListener {
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
        mConnect =  context as connectEdit

        super.onAttach(context)
    }

}