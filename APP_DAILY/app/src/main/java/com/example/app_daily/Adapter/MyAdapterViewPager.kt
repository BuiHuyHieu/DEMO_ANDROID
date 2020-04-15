package com.example.app_daily.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.app_daily.model.MakeToast

class MyAdapterViewPager: FragmentPagerAdapter {
    lateinit var listFragment: List<Fragment>

    constructor(fm: FragmentManager, listFragment: List<Fragment>) : super(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.listFragment = listFragment
    }


    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

}