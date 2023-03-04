package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class ApproveLeaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approveleave)

        val bundle: Bundle? = intent.extras

        val name = bundle!!.getString("Name").toString()
        val leaveType = bundle.getString("Leave").toString()
        val dateFrom = bundle.getString("StartDate").toString()
        val dateTo = bundle.getString("EndDate").toString()
        val noOfDays = bundle.getString("NoOfDays").toString()
        val reliever = bundle.getString("Reliever").toString()

        val txtViewLeaveName = findViewById<TextView>(R.id.txtViewLeaveName)
        val displayName = "NAME: $name"
        txtViewLeaveName.text = displayName

        val txtViewLeaveType = findViewById<TextView>(R.id.txtViewLeaveType)
        val displayLeave = "LEAVE: $leaveType"
        txtViewLeaveType.text = displayLeave

        val txtViewLeaveStartDate = findViewById<TextView>(R.id.txtViewLeaveStartDate)
        val displayStartDate = "START DATE: $dateFrom"
        txtViewLeaveStartDate.text = displayStartDate

        val txtViewLeaveEndDate= findViewById<TextView>(R.id.txtViewLeaveEndDate)
        val displayEndDate = "END DATE: $dateTo"
        txtViewLeaveEndDate.text = displayEndDate

        val txtViewLeaveReliever = findViewById<TextView>(R.id.txtViewLeaveReliever)
        val displayReliever = "RELIEVER: $reliever"
        txtViewLeaveReliever.text = displayReliever

        val txtViewLeaveNoOfDays = findViewById<TextView>(R.id.txtViewLeaveNoOfDays)
        val displayNoOfDays = "NO OF DAYS: $noOfDays"
        txtViewLeaveNoOfDays.text = displayNoOfDays

        val editTxtLeaveAdminKey = findViewById<EditText>(R.id.editTxtLeaveAdminKey)

        val btnFinishLeaveApplication = findViewById<Button>(R.id.btnFinishLeaveApplication)
        btnFinishLeaveApplication.setOnClickListener {
            val adminLeaveKey = "0ff1c3-sp4c3"

            val db = DBHelper(this, null)
            val cursor = db.getLoginDetails()

            if(editTxtLeaveAdminKey.text.toString().trim().isEmpty() || editTxtLeaveAdminKey.text.toString().trim() != adminLeaveKey ){
                Toast.makeText(this,"ENTER VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
            }  else if(editTxtLeaveAdminKey.text.toString().trim() == adminLeaveKey){
                if(cursor!!.moveToFirst()){
                    do{
                        val nameCheck1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val accountTagCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                        if(accountTagCheck.toString() == "ADMINISTRATOR"){
                            val authorizedBy = nameCheck1.toString()

                            val db1 = DBHelper3(this, null)
                            db1.addDetails(name, leaveType, dateFrom, dateTo, noOfDays, reliever, authorizedBy)

                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this,"LEAVE APPLICATION SUCCESSFUL",Toast.LENGTH_SHORT).show()
                            break
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
    }
}