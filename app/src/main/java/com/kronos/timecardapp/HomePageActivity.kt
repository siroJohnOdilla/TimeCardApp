package com.kronos.timecardapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomePageActivity : AppCompatActivity(){
    private lateinit var txtDisplayCurrentDate: TextView
    private lateinit var txtDisplayCurrentDay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val bundle: Bundle? = intent.extras
        val displayLoginName = bundle!!.getString("nameLogInKey")
        val displayLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey")
        val displayLogInDepartment = bundle.getString("displayDepartmentKey")
        val displayJobTitle = bundle.getString("displayJobTitleKey")
        val displayCompanyName = bundle.getString("displayCompanyNameKey")

        val txtDisplayNameLogin = findViewById<TextView>(R.id.txtDisplayNameLogin)
        txtDisplayNameLogin.text = displayLoginName

        val txtDisplayJobTitle = findViewById<TextView>(R.id.txtDisplayJobTitle)
        txtDisplayJobTitle.text = (displayJobTitle.toString()) + " ( " + (displayLogInDepartment.toString() + " )")

        val txtDisplayCompanyName = findViewById<TextView>(R.id.txtDisplayCompanyName)
        txtDisplayCompanyName.text = displayCompanyName

        val txtDisplayOfficeSiteBranch = findViewById<TextView>(R.id.txtDisplayOfficeSiteBranch)
        txtDisplayOfficeSiteBranch.text = displayLoginOfficeSiteBranch

        txtDisplayCurrentDay = findViewById(R.id.txtDisplayCurrentDay)
        val dayDisplay = SimpleDateFormat(" EEEE ",Locale.getDefault())
        val getCurrentDay = dayDisplay.format(Date())
        txtDisplayCurrentDay.text = getCurrentDay

        txtDisplayCurrentDate= findViewById(R.id.txtDisplayCurrentDate)
        val dateDisplay = SimpleDateFormat(" MMMM dd, yyyy ",Locale.getDefault())
        val getCurrentDate = dateDisplay.format(Date())
        txtDisplayCurrentDate.text = getCurrentDate


    }
}