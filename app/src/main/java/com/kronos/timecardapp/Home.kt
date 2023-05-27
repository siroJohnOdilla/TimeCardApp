package com.kronos.timecardapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*

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

        val displayLoginName = bundle!!.getString("nameLogInKey").toString()
        val displayLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey")
        val displayLogInDepartment = bundle.getString("displayDepartmentKey")
        val displayJobTitle = bundle.getString("displayJobTitleKey")
        val displayCompanyName = bundle.getString("displayCompanyNameKey")

        /*val bundle1 = Bundle().apply{
            putString("recentName", displayLoginName)
        }
        val fragmentTransaction = this.parentFragmentManager.beginTransaction()
        val recentFragment = RecentActivities()
        recentFragment.arguments = bundle1
        fragmentTransaction.replace(R.id.frameRecent, recentFragment)
        //fragmentTransaction.addToBackStack(null)
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()*/

        txtDisplayNameLogin = v.findViewById(R.id.txtDisplayNameLogin)
        txtDisplayNameLogin.text = displayLoginName

        txtDisplayJobTitle = v.findViewById(R.id.txtDisplayJobTitle)
        val display = "${displayJobTitle.toString()} (${displayLogInDepartment.toString()})"
        txtDisplayJobTitle.text = display

        txtDisplayCompanyName = v.findViewById(R.id.txtDisplayCompanyName)
        val display1 = "${displayCompanyName.toString()} (${displayLoginOfficeSiteBranch.toString()})"
        txtDisplayCompanyName.text = display1

        val recyclerViewRecent = v.findViewById<RecyclerView>(R.id.recyclerViewRecent)
        recyclerViewRecent.layoutManager = LinearLayoutManager(v.context)

        val data = ArrayList<ItemsViewModel6>()

        val db = DBHelper2(v.context,null)
        val cursor = db.getDetails()

        if(cursor!!.moveToLast()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                val timeOutCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                val totalCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TOTAL_TIME_WORKED_COL))
                val dateCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))

                if(displayLoginName == namePrint.toString()){
                    if(totalCheck.toString() != "to be set"){
                        val recentTimeOut = timeOutCheck.toString()
                        val recentTimeIn = timeInCheck.toString()
                        val recentTotalTime = totalCheck.toString()
                        val recentDate = dateCheck.toString()

                        data.add(ItemsViewModel6(recentTimeOut, recentTimeIn, recentTotalTime, recentDate))

                    } else if(totalCheck.toString() == "to be set"){
                        val recentTimeOut = ""
                        val recentTimeIn = timeInCheck.toString()
                        val recentTotalTime = ""
                        val recentDate = dateCheck.toString()

                        data.add(ItemsViewModel6(recentTimeOut, recentTimeIn, recentTotalTime, recentDate))
                    }
                }
            } while(cursor.moveToPrevious())
        }
        cursor.close()

        val adapter = CustomAdapter7(data)
        recyclerViewRecent.adapter = adapter

        adapter.setOnClickListener(object: CustomAdapter7.onItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })

        /*tabLayoutTimeCard = v.findViewById(R.id.tabLayoutTimeCard)
        viewPagerTimeCard = v.findViewById(R.id.viewPagerTimeCard)

        tabLayoutTimeCard.addTab(tabLayoutTimeCard.newTab().setText("RECENT"))
        tabLayoutTimeCard.addTab(tabLayoutTimeCard.newTab().setText("STATS"))

        tabLayoutTimeCard.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = CustomAdapter6(v.context, parentFragmentManager, tabLayoutTimeCard.tabCount)
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

        })*/
        return v
    }
}

