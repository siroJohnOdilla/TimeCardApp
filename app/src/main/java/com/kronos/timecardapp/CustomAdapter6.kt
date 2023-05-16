package com.kronos.timecardapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomAdapter6(var context: Context, fm: FragmentManager, var totalTabs: Int) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                RecentActivities()
            }
            1 -> {
                Statistics()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}