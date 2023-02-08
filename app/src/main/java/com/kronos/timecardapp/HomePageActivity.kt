package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class HomePageActivity : AppCompatActivity() {
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
        txtDisplayJobTitle.text = "${displayJobTitle.toString()} (${displayLogInDepartment.toString()})"

        val txtDisplayCompanyName = findViewById<TextView>(R.id.txtDisplayCompanyName)
        txtDisplayCompanyName.text = displayCompanyName

        val txtDisplayOfficeSiteBranch = findViewById<TextView>(R.id.txtDisplayOfficeSiteBranch)
        txtDisplayOfficeSiteBranch.text = displayLoginOfficeSiteBranch

        txtDisplayCurrentDay = findViewById(R.id.txtDisplayCurrentDay)
        val dayDisplay = SimpleDateFormat(" EEEE ", Locale.getDefault())
        val getCurrentDay = dayDisplay.format(Date())
        txtDisplayCurrentDay.text = getCurrentDay

        txtDisplayCurrentDate = findViewById(R.id.txtDisplayCurrentDate)
        val dateDisplay = SimpleDateFormat(" MMMM dd, yyyy ", Locale.getDefault())
        val getCurrentDate = dateDisplay.format(Date())
        txtDisplayCurrentDate.text = getCurrentDate

        val btnClockIn = findViewById<Button>(R.id.btnClockIn)
        btnClockIn.setOnClickListener {
            val db = DBHelper2(this,null)

            val makeDateFormat = SimpleDateFormat("dd - MM - yyyy", Locale.getDefault())
            val date = makeDateFormat.format(Date())

            val makeTimeFormat = SimpleDateFormat("HH : mm : ss", Locale.getDefault())
            val timeIn = makeTimeFormat.format(Date())

            val name = displayLoginName.toString()
            val officeBranchSite = displayLoginOfficeSiteBranch.toString()
            val department = displayLogInDepartment.toString()
            val jobTitle = displayJobTitle.toString()

            val timeOut = "to be set"
            val totalTimeWorked = "to be set"

            db.addDetails(date, name, jobTitle, department, officeBranchSite, timeIn, timeOut, totalTimeWorked)

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

            Toast.makeText(this,"TIME IN: $timeIn",Toast.LENGTH_SHORT).show()
        }

        val btnClockOut = findViewById<Button>(R.id.btnClockOut)
        btnClockOut.setOnClickListener {
            val makeDateFormat = SimpleDateFormat("dd - MM - yyyy", Locale.getDefault())
            val dateCheck = makeDateFormat.format(Date())

            val db = DBHelper2(this, null)
            val cursor = db.getDetails()

            if (cursor!!.moveToFirst()) {
                do {
                    val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                    val checkDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                    val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                    val timeOutCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                    val idCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.ID_COL))

                    if (displayLoginName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && timeInCheck.toString().isNotEmpty() && timeOutCheck.toString() == "to be set") {
                        val id = idCheck.toLong()

                        val makeTimeFormat = SimpleDateFormat("HH : mm : ss", Locale.getDefault())
                        val timeOut = makeTimeFormat.format(Date())

                        val d1: Date = makeTimeFormat.parse(timeInCheck) as Date
                        val d2: Date = makeTimeFormat.parse(timeOut) as Date

                        val difference: Long = abs(d2.time - d1.time)
                        //val differenceDays= difference / (24*60*60*1000)
                        val differenceHours = (difference / (1000 * 60 * 60)) % 24
                        val differenceMinutes = (difference / (1000 * 60)) % 60
                        val differenceSeconds = (difference / 1000) % 60

                        val totalTimeWorked = ("$differenceHours HRS $differenceMinutes MIN $differenceSeconds SEC")

                        db.updateClockIn(id, timeOut, totalTimeWorked)
                        db.close()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "TIME OUT: $timeOut", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "TOTAL TIME WORKED: $totalTimeWorked", Toast.LENGTH_SHORT).show()
                        cursor.close()

                    } else if (displayLoginName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && timeInCheck.toString().isNotEmpty() && timeOutCheck.toString().isNotEmpty()) {
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this,"YOU HAVE ALREADY CLOCKED OUT",Toast.LENGTH_SHORT).show()
                        cursor.close()
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
    }
}

