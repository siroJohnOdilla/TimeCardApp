package com.kronos.timecardapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView

class HomePageDrawerActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var displayActionBarTitle: String
    private lateinit var getName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepagedrawer)

        val actionBar = supportActionBar
        displayActionBarTitle = "Home"
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
        val navView = findViewById<NavigationView>(R.id.nav_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            drawer.closeDrawers()
            when(it.itemId){
                R.id.itemHome -> {
                    displayActionBarTitle = "Home"
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
                R.id.itemTimeAttendance -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "Attendance Records"
                        actionBar.title = displayActionBarTitle

                        supportFragmentManager.beginTransaction().replace(R.id.frame,TimeAttendance()).commit()
                    }
                }
                R.id.itemLeaveSchedule -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "Leave Schedule"
                        actionBar.title = displayActionBarTitle

                        val passName = getName

                        val fragmentManager2: FragmentManager = supportFragmentManager
                        val fragmentTransaction1: FragmentTransaction = fragmentManager2.beginTransaction()

                        val myLeaveScheduleFragment = LeaveSchedule()

                        val bundle3 = Bundle()

                        bundle3.putString("nameToCheck",passName)

                        myLeaveScheduleFragment.arguments = bundle3
                        fragmentTransaction1.replace(R.id.frame, myLeaveScheduleFragment).commit()
                    }
                }
                R.id.itemProfileView -> {

                }
                R.id.itemProfile -> {
                    if(passAccountTag == "USER"){
                        Toast.makeText(this,"ACCESS DENIED",Toast.LENGTH_SHORT).show()
                    } else{
                        displayActionBarTitle = "Profiles"
                        actionBar.title = displayActionBarTitle

                        supportFragmentManager.beginTransaction().replace(R.id.frame,Profile()).commit()
                    }
                }
                R.id.itemSignOut -> {
                    onBackPressed()
                }
                R.id.itemVisitorBook -> {
                    displayActionBarTitle = "Visitor Book"
                    actionBar.title = displayActionBarTitle

                    val passName = getName

                    val fragmentManager1: FragmentManager = supportFragmentManager
                    val fragmentTransaction1: FragmentTransaction = fragmentManager1.beginTransaction()

                    val myVisitorBookFragment = VisitorBook()

                    val bundle2 = Bundle()

                    bundle2.putString("nameToCheck",passName)

                    myVisitorBookFragment.arguments = bundle2
                    fragmentTransaction1.replace(R.id.frame, myVisitorBookFragment).commit()
                }
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        displaySignOut()
    }
    private fun displaySignOut(){
        val dialog = BottomSheetDialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheet_signout)

        val btnNoSignOut = dialog.findViewById<Button>(R.id.btnNoSignOut)
        if (btnNoSignOut != null) {
            btnNoSignOut.setOnClickListener {
                dialog.dismiss()
            }
        }

        val btnYesSignOut = dialog.findViewById<Button>(R.id.btnYesSignOut)
        if (btnYesSignOut != null) {
            btnYesSignOut.setOnClickListener {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)

            }
        }
        dialog.show()
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }
}