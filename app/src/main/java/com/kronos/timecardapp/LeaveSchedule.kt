package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class LeaveSchedule : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_leaveschedule, container, false)

        val bundle = arguments

        val loginNameCheck = bundle!!.getString("nameLogIn")

        val btnApplyForLeave = v.findViewById<Button>(R.id.btnApplyForLeave)
        btnApplyForLeave.setOnClickListener {
            val intent = Intent(v.context,ApplyLeaveActivity::class.java)

            intent.putExtra("nameToCheck",loginNameCheck)
            startActivity(intent)

            Toast.makeText(v.context,"APPLY FOR LEAVE",Toast.LENGTH_SHORT).show()
        }
        
        val btnViewLeaveSchedule = v.findViewById<Button>(R.id.btnViewLeaveSchedule)
        btnViewLeaveSchedule.setOnClickListener {
            val intent = Intent(v.context,LeaveScheduleViewActivity::class.java)
            startActivity(intent)
            Toast.makeText(v.context,"LEAVE SCHEDULE",Toast.LENGTH_SHORT).show()
        }
        return v
    }
}