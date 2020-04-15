package com.example.app_daily.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.app_daily.R
import com.example.app_daily.model.MakeToast
import kotlinx.android.synthetic.main.fragment_rating.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class fragment_rating : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        view.rating_Feedback.setSelectedSmile(loadSharedPrefFb(),false)
        if((Calendar.getInstance().get(Calendar.DATE))== activity!!.getSharedPreferences("rating", MODE_PRIVATE).getInt("check_day",0))

            updateSharedPref(false,4)
        else
            view.rating_Feedback.isIndicator = loadSharedPrefCheck()


            view.rating_Feedback.setOnRatingSelectedListener {
                    level, reselected ->
                when(level)
                {
                    1-> MakeToast.make(activity!!,"Oh Sorry :( Everything is bad")
                    2-> MakeToast.make(activity!!,"Hum~ We will review it")
                    3-> MakeToast.make(activity!!,"Ok ! That Ok")
                    4-> MakeToast.make(activity!!,"Great ! Thank you~")
                    5-> MakeToast.make(activity!!,"Awesome ! Love you~")
                    else -> MakeToast.make(activity!!,"Please send me Feedback")
                }
            }
            view.bt_FeedBack.setOnClickListener {
                val currentFb = view.rating_Feedback.selectedSmile
                updateSharedPref(true,currentFb)
                MakeToast.make(activity!!,"Feedback was send ")
                view.rating_Feedback.isIndicator = loadSharedPrefCheck()
            }
            return view
        }

    private fun updateSharedPref(check:Boolean,currentFb:Int)
    {
        val sharedPreferences = activity!!.getSharedPreferences("rating",MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putBoolean("check_buttonFeedBack",check)
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE)
        val tomorrow = date.plus(1)
        Log.d("Debug", "$date $tomorrow")
        edit.putInt("check_day",tomorrow)
        edit.putInt("current_fb",currentFb)
        edit.apply()
        edit.commit()
    }
    private fun loadSharedPrefCheck():Boolean{
        return activity!!.getSharedPreferences("rating", MODE_PRIVATE).getBoolean("check_buttonFeedBack",false)
    }
    private fun loadSharedPrefFb():Int{
        return activity!!.getSharedPreferences("rating", MODE_PRIVATE).getInt("current_fb",3)
    }

}
