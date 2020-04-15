package com.example.app_daily.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ToxicBakery.viewpager.transforms.*
import com.example.app_daily.Adapter.MyAdapterViewPager
import com.example.app_daily.Class.Note
import com.example.app_daily.Database.DatabaseNote
import com.example.app_daily.Interface.OnDeleteItemListener
import com.example.app_daily.Interface.OnEditItemListener
import com.example.app_daily.R
import com.example.app_daily.fragment.*
import com.example.app_daily.model.Display
import com.example.app_daily.model.MakeToast
import kotlinx.android.synthetic.main.activity_main_app.*

class main_app : AppCompatActivity()
    ,fragment_addnote.connect
    ,OnDeleteItemListener
    ,fragment_editnote.connectEdit
    ,OnEditItemListener {
    lateinit var myAdapterViewPager: MyAdapterViewPager
    private val TAG="Debug"
    lateinit var currentNote:Note
    var listNote:ArrayList<Note> = ArrayList<Note>()
    lateinit var listFragment: ArrayList<Fragment>
    lateinit var database:DatabaseNote
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)
        bottom_navigation_constraint.setNavigationChangeListener { view, position ->
            when (view) {
                bottom_home -> {
                    myViewPage.currentItem = 0
                }
                bottom_profile -> {
                    myViewPage.currentItem = 1
                }
                bottom_rating -> {
                    myViewPage.currentItem = 2
                }
                bottom_contact -> {
                    myViewPage.currentItem = 3
                }
            }
        }
        setViewPager()

    }

    override fun onRestart() {
        super.onRestart()
        Display.fullScreen(this)
        Log.d(TAG,"On Restart")
    }
    override fun onStart() {
        super.onStart()
        Display.fullScreen(this)
        Log.d(TAG,"On Start")
    }

    override fun onResume() {
        super.onResume()
        Display.fullScreen(this)
        Log.d(TAG,"On Resume")
    }

    override fun onPause() {
        super.onPause()
        Display.fullScreen(this)
        Log.d(TAG,"On Pause")
    }

    override fun onStop() {
        super.onStop()
        Display.fullScreen(this)
        Log.d(TAG,"On Stop")
    }
    private fun setViewPager() {
        listFragment = ArrayList()
        val fragment_home = fragment_home()
        val fragment_contact = fragment_contact()
        val fragment_Profile = fragment_profile()
        val fragment_rating = fragment_rating()
        listFragment.add(fragment_home)
        listFragment.add(fragment_Profile)
        listFragment.add(fragment_rating)
        listFragment.add(fragment_contact)
        myAdapterViewPager = MyAdapterViewPager(supportFragmentManager,listFragment)
        myViewPage.adapter = myAdapterViewPager
        myViewPage.setPageTransformer(true,object:ZoomOutTransformer(){})
        myViewPage.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottom_navigation_constraint.setCurrentActiveItem(position)
            }


        })
    }

    override fun inputEdit(note: Note) {
       database = DatabaseNote(this)
       database.updateData(currentNote.id!!,note.title!!,note.time!!,note.place!!,note.description!!)
        setViewPager()
    }

    override fun sendNote(note:Note) {
        currentNote = note
        val fragment_editnote = fragment_editnote()
        fragment_editnote.show(supportFragmentManager,"edit_note")
    }

    override fun input() {
        setViewPager()
    }

    override fun sendInput(note: Note) {
        database = DatabaseNote(this)
        database.deleteData(note.id!!)
        setViewPager()
    }
}



