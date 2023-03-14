package com.kronos.timecardapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TimeAttendanceViewActivity : AppCompatActivity() {
    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeattendanceview)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        val startDateRange = intent.getStringExtra("StartDate").toString()
        val endDateRange = intent.getStringExtra("EndDate").toString()
        val nameFilter = intent.getStringExtra("FullName").toString()

        if(startDateRange.isNotEmpty() && endDateRange.isNotEmpty()){
            val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy",Locale.getDefault())
            startDate = LocalDate.parse(startDateRange,dtf) as LocalDate
            endDate = LocalDate.parse(endDateRange,dtf) as LocalDate

            val recyclerViewTimeAttendance = findViewById<RecyclerView>(R.id.recyclerViewTimeAttendance)
            recyclerViewTimeAttendance.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel2>()

            var date = "DATE"
            var name = "NAME"
            var officeSiteBranch = "OFFICE/SITE"
            var department = "DEPARTMENT"
            var jobTitle = "JOB TITLE"
            var timeIn = "TIME IN"
            var timeOut = "TIME OUT"
            var timeWorked = "TOTAL TIME WORKED"

            data.add(ItemViewModel2(date, name, officeSiteBranch, department, jobTitle, timeIn, timeOut, timeWorked))

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

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty()) && (testDate.isAfter(startDate) || testDate.isEqual(startDate)) && (testDate.isBefore(endDate) || testDate.isEqual(endDate)) && timeWorkedPrint.toString() != "to be set"){
                        date = datePrint.toString()
                        name = namePrint.toString()
                        officeSiteBranch = siteBranchPrint.toString()
                        department = departmentPrint.toString()
                        jobTitle = jobTitlePrint.toString()
                        timeIn = timeInPrint.toString()
                        timeOut = timeOutPrint.toString()
                        timeWorked = timeWorkedPrint.toString()

                        data.add(ItemViewModel2(date, name, officeSiteBranch, department, jobTitle, timeIn, timeOut, timeWorked))
                    }
                } while(cursor.moveToNext())
            }
            cursor.close()

            val adapter = CustomAdapter2(data)
            recyclerViewTimeAttendance.adapter = adapter
        } else if(startDateRange.isEmpty() && endDateRange.isEmpty()){
            val recyclerViewTimeAttendance = findViewById<RecyclerView>(R.id.recyclerViewTimeAttendance)
            recyclerViewTimeAttendance.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel2>()

            var date = "DATE"
            var name = "NAME"
            var officeSiteBranch = "OFFICE/SITE"
            var department = "DEPARTMENT"
            var jobTitle = "JOB TITLE"
            var timeIn = "TIME IN"
            var timeOut = "TIME OUT"
            var timeWorked = "TOTAL TIME WORKED"

            data.add(ItemViewModel2(date, name, officeSiteBranch, department, jobTitle, timeIn, timeOut, timeWorked))

            val db = DBHelper2(this,null)
            val cursor = db.getDetails()

            if(cursor!!.moveToFirst()){
                do{
                    val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                    val siteBranchPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.OFFICE_SITE_COL))
                    val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DEPARTMENT_COL))
                    val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.JOB_TITLE_COL))
                    val timeInPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                    val timeOutPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                    val timeWorkedPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TOTAL_TIME_WORKED_COL))

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty()) && timeWorkedPrint.toString() != "to be set"){
                        date = datePrint.toString()
                        name = namePrint.toString()
                        officeSiteBranch = siteBranchPrint.toString()
                        department = departmentPrint.toString()
                        jobTitle = jobTitlePrint.toString()
                        timeIn = timeInPrint.toString()
                        timeOut = timeOutPrint.toString()
                        timeWorked = timeWorkedPrint.toString()

                        data.add(ItemViewModel2(date, name, officeSiteBranch, department, jobTitle, timeIn, timeOut, timeWorked))
                    }
                } while(cursor.moveToNext())
            }
            cursor.close()

            val adapter = CustomAdapter2(data)
            recyclerViewTimeAttendance.adapter = adapter
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}