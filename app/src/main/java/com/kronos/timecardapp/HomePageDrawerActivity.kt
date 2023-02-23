package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class HomePageDrawerActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepagedrawer)

        val bundle: Bundle? = intent.extras

        val passLoginName = bundle!!.getString("nameLogInKey1")
        val passLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey1")
        val passLogInDepartment = bundle.getString("displayDepartmentKey1")
        val passJobTitle = bundle.getString("displayJobTitleKey1")
        val passCompanyName = bundle.getString("displayCompanyNameKey1")

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val myHomeFragment = Home()

        val bundle1 = Bundle()

        bundle1.putString("nameLogInKey",passLoginName)
        bundle1.putString("displayOfficeSiteBranchKey",passLoginOfficeSiteBranch)
        bundle1.putString("displayDepartmentKey",passLogInDepartment)
        bundle1.putString("displayJobTitleKey",passJobTitle)
        bundle1.putString("displayCompanyNameKey",passCompanyName)

        myHomeFragment.arguments = bundle1
        fragmentTransaction.replace(R.id.frame, myHomeFragment).commit()

        /*val bundle: Bundle? = intent.extras

        val passLoginName = bundle!!.getString("nameLogInKey1")
        val passLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey1")
        val passLogInDepartment = bundle.getString("displayDepartmentKey1")
        val passJobTitle = bundle.getString("displayJobTitleKey1")
        val passCompanyName = bundle.getString("displayCompanyNameKey1")

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val myFragment = Home()
        val bundle1 = Bundle()

        bundle1.putString("nameLogInKey",passLoginName)
        bundle1.putString("displayOfficeSiteBranchKey",passLoginOfficeSiteBranch)
        bundle1.putString("displayDepartmentKey",passLogInDepartment)
        bundle1.putString("displayJobTitleKey",passJobTitle)
        bundle1.putString("displayCompanyNameKey",passCompanyName)

        myFragment.arguments = bundle1
        fragmentTransaction.add(R.id.frame, myFragment).commit()*/

        val drawer = findViewById<DrawerLayout>(R.id.drawer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val tabs = arrayOf("HOME","TIME ATTENDANCE","LEAVE SCHEDULE","PROFILE","SETTINGS","SIGN OUT")
        val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,tabs)
        val listview = findViewById<ListView>(R.id.listView)
        listview.adapter = adapter
        
        listview.setOnItemClickListener {adapterView, view, i, l ->
            drawer.closeDrawers()
            when(i){
                0 -> {
                    val fragmentManager: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

                    val myHomeFragment = Home()

                    val bundle1 = Bundle()

                    bundle1.putString("nameLogInKey",passLoginName)
                    bundle1.putString("displayOfficeSiteBranchKey",passLoginOfficeSiteBranch)
                    bundle1.putString("displayDepartmentKey",passLogInDepartment)
                    bundle1.putString("displayJobTitleKey",passJobTitle)
                    bundle1.putString("displayCompanyNameKey",passCompanyName)

                    myHomeFragment.arguments = bundle1
                    fragmentTransaction.replace(R.id.frame, myHomeFragment).commit()
                }
                1 -> {supportFragmentManager.beginTransaction().replace(R.id.frame,TimeAttendance()).commit()}
                2 -> {supportFragmentManager.beginTransaction().replace(R.id.frame,LeaveSchedule()).commit()}
                3 -> {supportFragmentManager.beginTransaction().replace(R.id.frame,Profile()).commit()}
                4 -> {supportFragmentManager.beginTransaction().replace(R.id.frame,Settings()).commit()}
                5 -> {
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(this,"$passLoginName\nHAS SIGNED OUT",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}