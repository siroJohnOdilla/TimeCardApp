package com.kronos.timecardapp

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TimeAttendanceViewActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeattendanceview)

        val intent = intent

        val startDateRange = intent.getStringExtra("StartDate").toString()
        val endDateRange = intent.getStringExtra("EndDate").toString()

        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy",Locale.getDefault())
        val startDate: LocalDate = LocalDate.parse(startDateRange,dtf) as LocalDate
        val endDate: LocalDate = LocalDate.parse(endDateRange,dtf) as LocalDate

        //val txtTableTitle = findViewById<TextView>(R.id.txtTableTitle)
        //txtTableTitle.text = startDateRange

        //val nameFilter = bundle.getString("FullNameNationalId")

        val db = DBHelper2(this,null)
        val cursor = db.getDetails()

        if(cursor!!.moveToFirst()){
            do{
                val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                val dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy",Locale.getDefault())
                val testDate = LocalDate.parse(datePrint,dtf2)

                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                val siteBranchPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.OFFICE_SITE_COL))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DEPARTMENT_COL))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.JOB_TITLE_COL))
                val timeInPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                val timeOutPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                val timeWorkedPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TOTAL_TIME_WORKED_COL))

                if(namePrint.toString().isNotEmpty() && timeWorkedPrint.toString() != "to be set" && (testDate.isAfter(startDate) || testDate.isEqual(startDate)) && (testDate.isBefore(endDate) || testDate.isEqual(endDate))){
                    val date = datePrint.toString()
                    val name = namePrint.toString()
                    val siteBranch = siteBranchPrint.toString()
                    val department = departmentPrint.toString()
                    val jobTitle = jobTitlePrint.toString()
                    val timeIn = timeInPrint.toString()
                    val timeOut = timeOutPrint.toString()
                    val timeWorked = timeWorkedPrint.toString()

                    val dateTable = findViewById<TextView>(R.id.DateTable)
                    dateTable.text = "$date\n "

                    val nameTable = findViewById<TextView>(R.id.NameTable)
                    nameTable.text = "$name\n "

                    val siteBranchTable = findViewById<TextView>(R.id.siteBranchTable)
                    siteBranchTable.text = "$siteBranch\n "

                    val departmentTable = findViewById<TextView>(R.id.DepartmentTable)
                    departmentTable.text = "$department\n "

                    val jobTitleTable = findViewById<TextView>(R.id.JobTitleTable)
                    jobTitleTable.text = "$jobTitle\n "

                    val timeInTable = findViewById<TextView>(R.id.TimeInTable)
                    timeInTable.text = "$timeIn\n "

                    val timeOutTable = findViewById<TextView>(R.id.TimeOutTable)
                    timeOutTable.text = "$timeOut\n "

                    val totalTimeWorkedTable = findViewById<TextView>(R.id.TotalTimeWorkedTable)
                    totalTimeWorkedTable.text = "$timeWorked\n "
                }
            } while(cursor.moveToNext())
        }
        cursor.close()
    }
}