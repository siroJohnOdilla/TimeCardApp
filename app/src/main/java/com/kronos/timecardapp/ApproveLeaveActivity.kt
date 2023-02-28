package com.kronos.timecardapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ApproveLeaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approveleave)

        val bundle: Bundle? = intent.extras

        val displayName = bundle!!.getString("Name")
        val displayLeave = bundle.getString("Leave")
        val displayStartDate = bundle.getString("StartDate")
        val displayEndDate = bundle.getString("EndDate")
        val displayReliever = bundle.getString("Reliever")
        val displayNoOfDays = bundle.getString("NoOfDays")

        val txtViewLeaveName = findViewById<TextView>(R.id.txtViewLeaveName)
        txtViewLeaveName.text = "NAME: $displayName"
        val txtViewLeaveType = findViewById<TextView>(R.id.txtViewLeaveType)
        txtViewLeaveType.text = "LEAVE: $displayLeave"
        val txtViewLeaveStartDate = findViewById<TextView>(R.id.txtViewLeaveStartDate)
        txtViewLeaveStartDate.text = "START DATE: $displayStartDate"
        val txtViewLeaveEndDate= findViewById<TextView>(R.id.txtViewLeaveEndDate)
        txtViewLeaveEndDate.text = "END DATE: $displayEndDate"
        val txtViewLeaveReliever = findViewById<TextView>(R.id.txtViewLeaveReliever)
        txtViewLeaveReliever.text = "RELIEVER: $displayReliever"
        val txtViewLeaveNoOfDays = findViewById<TextView>(R.id.txtViewLeaveNoOfDays)
        txtViewLeaveNoOfDays.text = "NO OF DAYS: $displayNoOfDays"

    }
}