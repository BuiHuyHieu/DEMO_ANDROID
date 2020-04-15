package com.example.app_daily.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_daily.Adapter.RecyclerViewAdapter
import com.example.app_daily.Class.Note
import com.example.app_daily.Database.DatabaseNote
import com.example.app_daily.R
import com.example.app_daily.activity.main_app
import com.example.app_daily.model.Display
import com.example.app_daily.model.MakeToast

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.floatingActionButton
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class fragment_home : Fragment(),View.OnClickListener {
    lateinit var adapter:RecyclerViewAdapter
    private var listNote: ArrayList<Note> = ArrayList()
    lateinit var database:DatabaseNote
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        database = DatabaseNote(activity!!)
        listNote = database.readData()
        adapter = RecyclerViewAdapter(view.context,listNote)
        view.floatingActionButton.setOnClickListener(this)
        val layoutParams = LinearLayoutManager(activity)
        layoutParams.orientation = LinearLayoutManager.VERTICAL
        view.recyclerView.layoutManager = layoutParams
        view.recyclerView.adapter = adapter
        return view
    }

    override fun onResume() {
        Log.d("Debug","Fragment Home: On Resume")
        Display.fullScreen(activity!!)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Debug","Fragment Home: On Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Debug","Fragment Home: On Pause")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Debug","Fragment: On Start")
        Display.fullScreen(activity!!)
    }

    override fun onClick(v: View?) {
        when(v)
        {
            floatingActionButton->
            {
                val fragmentAdd = fragment_addnote()
                fragmentAdd.show(fragmentManager!!,"add_note")
            }
        }
    }


}
