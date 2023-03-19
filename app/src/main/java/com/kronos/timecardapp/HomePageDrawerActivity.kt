package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
    private lateinit var displayActionBarTitle: String
    private lateinit var getName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepagedrawer)

        val actionBar = supportActionBar
        displayActionBarTitle = "HOME"
        actionBar!!.title = displayActionBarTitle

        val bundle: Bundle? = intent.extras

        val passLoginName = bundle!!.getString("nameLogInKey1").toString()
        getName = passLoginName
        val passLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey1")
        val passLogInDepartment = bundle.getString("displayDepartmentKey1")
        val passJobTitle = bundle.getString("displayJobTitleKey1")
        val passAccountTag = bundle.getString("accountTagCheck1")
        val passCompanyName = bundle.getString("displayCompanyNameKey1")

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val myHomeFragment = Home()

        val bundle1 = Bundle()

        bundle1.putString("nameLogInKey",passLoginName)
        bundle1.putString("displayOfficeSiteBranchKey",passLoginOfficeSiteBranch)
        bundle1.putString("displayDepartmentKey",passLogInDepartment)
        bundle1.putString("displayJobTitleKey",passJobTitle)
        bundle1.putString("accountTagCheck",passAccountTag)
        bundle1.putString("displayCompanyNameKey",passCompanyName)

        myHomeFragment.arguments = bundle1

        fragmentTransaction.replace(R.id.frame, myHomeFragment).commit()

        val drawer = findViewById<DrawerLayout>(R.id.drawer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val tabs = arrayOf("HOME","TIME ATTENDANCE","LEAVE SCHEDULE","PROFILE")
        val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,tabs)
        val listview = findViewById<ListView>(R.id.listView)
        listview.adapter = adapter
        
        listview.setOnItemClickListener {adapterView, view, i, l ->
            drawer.closeDrawers()
            when(i){
                0 -> {
                    displayActionBarTitle = "HOME"
                    actionBar.title = displayActionBarTitle

                    val fragmentManager: FragmentManager = supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

                    val myHomeFragment = Home()

                    val bundle1 = Bundle()

                    bundle1.putString("nameLogInKey",passLoginName)
                    bundle1.putString("displayOfficeSiteBranchKey",passLoginOfficeSiteBranch)
                    bundle1.putString("displayDepartmentKey",passLogInDepartment)
                    bundle1.putString("displayJobTitleKey",passJobTitle)
                    bundle1.putString("accountTagCheck",passAccountTag)
                    bundle1.putString("displayCompanyNameKey",passCompanyName)

                    myHomeFragment.arguments = bundle1
                    fragmentTransaction.replace(R.id.frame, myHomeFragment).commit()
                }
                1 -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "TIME ATTENDANCE"
                        actionBar.title = displayActionBarTitle

                        supportFragmentManager.beginTransaction().replace(R.id.frame,TimeAttendance()).commit()
                    }

                }
                2 -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "LEAVE SCHEDULE"
                        actionBar.title = displayActionBarTitle

                        supportFragmentManager.beginTransaction().replace(R.id.frame,LeaveSchedule()).commit()
                    }

                }

                3 -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "PROFILE"
                        actionBar.title = displayActionBarTitle

                        supportFragmentManager.beginTransaction().replace(R.id.frame,Profile()).commit()
                    }
                }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.itemViewApplyLeave -> {
                val intent = Intent(this@HomePageDrawerActivity,ApplyLeaveActivity::class.java)

                val passName = getName
                intent.putExtra("nameToCheck",passName)

                startActivity(intent)
                Toast.makeText(this@HomePageDrawerActivity,"LEAVE APPLICATION",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.itemViewSettings -> {
                val intent = Intent(this@HomePageDrawerActivity,SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.itemViewSignOut -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this@HomePageDrawerActivity,PopUpWindowSignOutActivity::class.java)
        intent.putExtra("darkStatusBar", false)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagedrawer_option_menu, menu)
        return true
    }
}