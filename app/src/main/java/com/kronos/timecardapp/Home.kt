package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
class Home : Fragment(){
    private lateinit var txtDisplayNameLogin: TextView
    private lateinit var txtDisplayJobTitle: TextView
    private lateinit var txtDisplayCompanyName: TextView
    private lateinit var txtDisplayOfficeSiteBranch: TextView
    private lateinit var tabLayoutTimeCard: TabLayout
    private lateinit var viewPagerTimeCard: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)

        val bundle = arguments

        val displayLoginName = bundle!!.getString("nameLogInKey")
        val displayLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey")
        val displayLogInDepartment = bundle.getString("displayDepartmentKey")
        val displayJobTitle = bundle.getString("displayJobTitleKey")
        val displayCompanyName = bundle.getString("displayCompanyNameKey")

        val fragmentManager: FragmentManager = parentFragmentManager

        txtDisplayNameLogin = v.findViewById(R.id.txtDisplayNameLogin)
        txtDisplayNameLogin.text = displayLoginName

        txtDisplayJobTitle = v.findViewById(R.id.txtDisplayJobTitle)
        val display = "${displayJobTitle.toString()} (${displayLogInDepartment.toString()})"
        txtDisplayJobTitle.text = display

        txtDisplayCompanyName = v.findViewById(R.id.txtDisplayCompanyName)
        txtDisplayCompanyName.text = displayCompanyName

        txtDisplayOfficeSiteBranch = v.findViewById(R.id.txtDisplayOfficeSiteBranch)
        txtDisplayOfficeSiteBranch.text = displayLoginOfficeSiteBranch

        tabLayoutTimeCard = v.findViewById(R.id.tabLayoutTimeCard)
        viewPagerTimeCard = v.findViewById(R.id.viewPagerTimeCard)

        tabLayoutTimeCard.addTab(tabLayoutTimeCard.newTab().setText("RECENT"))
        tabLayoutTimeCard.addTab(tabLayoutTimeCard.newTab().setText("STATS"))

        tabLayoutTimeCard.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = CustomAdapter6(v.context, fragmentManager, tabLayoutTimeCard.tabCount)
        viewPagerTimeCard.adapter = adapter

        viewPagerTimeCard.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutTimeCard))
        tabLayoutTimeCard.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerTimeCard.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        return v
    }
}

