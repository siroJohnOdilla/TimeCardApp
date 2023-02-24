package com.kronos.timecardapp

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
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

        var dateArray = arrayOf("")
        var nameArray = arrayOf("")
        var officeSiteArray = arrayOf("")
        var departmentArray = arrayOf("")
        var jobTitleArray = arrayOf("")
        var timeInArray = arrayOf("")
        var timeOutArray = arrayOf("")
        var timeWorkedArray = arrayOf("")

        fun addElementDate(dateArray: Array<String>, element: String): Array<String>{
            val mutableArray = dateArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementName(nameArray: Array<String>, element: String): Array<String>{
            val mutableArray = nameArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementOfficeSite(officeSiteArray: Array<String>, element: String): Array<String>{
            val mutableArray = officeSiteArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementDepartment(departmentArray: Array<String>, element: String): Array<String>{
            val mutableArray = departmentArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementJobTitle(jobTitleArray: Array<String>, element: String): Array<String>{
            val mutableArray = jobTitleArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementTimeIn(timeInArray: Array<String>, element: String): Array<String>{
            val mutableArray = timeInArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementTimeOut(timeOutArray: Array<String>, element: String): Array<String>{
            val mutableArray = timeOutArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }
        fun addElementTotalTimeWorked(timeWorkedArray: Array<String>, element: String): Array<String>{
            val mutableArray = timeWorkedArray.toMutableList()
            mutableArray.add(element)
            return mutableArray.toTypedArray()
        }

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

                    dateArray = addElementDate(dateArray, date)
                    nameArray = addElementName(nameArray, name)
                    officeSiteArray = addElementOfficeSite(officeSiteArray, siteBranch)
                    departmentArray = addElementDepartment(departmentArray, department)
                    jobTitleArray = addElementJobTitle(jobTitleArray, jobTitle)
                    timeInArray = addElementTimeIn(timeInArray, timeIn)
                    timeOutArray = addElementTimeOut(timeOutArray, timeOut)
                    timeWorkedArray = addElementTotalTimeWorked(timeWorkedArray, timeWorked)
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        val arrayAdapterDate: ArrayAdapter<*>
        val arrayAdapterName: ArrayAdapter<*>
        val arrayAdapterSiteBranch: ArrayAdapter<*>
        val arrayAdapterDepartment: ArrayAdapter<*>
        val arrayAdapterJobTitle: ArrayAdapter<*>
        val arrayAdapterTimeIn: ArrayAdapter<*>
        val arrayAdapterTimeOut: ArrayAdapter<*>
        val arrayAdapterTotalTimeWorked: ArrayAdapter<*>

        val dateListView = findViewById<ListView>(R.id.dateListView)
        //dateListView.isEnabled = false
        val nameListView = findViewById<ListView>(R.id.nameListView)
        //nameListView.isEnabled = false
        val siteBranchListView = findViewById<ListView>(R.id.siteBranchListView)
        //siteBranchListView.isEnabled = false
        val departmentListView = findViewById<ListView>(R.id.departmentListView)
        //departmentListView.isEnabled = false
        val jobTitleListView = findViewById<ListView>(R.id.jobTitleListView)
        //jobTitleListView.isEnabled = false
        val timeInListView = findViewById<ListView>(R.id.timeInListView)
        //timeInListView.isEnabled = false
        val timeOutListView = findViewById<ListView>(R.id.timeOutListView)
        //timeOutListView.isEnabled = false
        val totalTimeWorkedListView = findViewById<ListView>(R.id.totalTimeWorkedListView)
        //totalTimeWorkedListView.isEnabled = false

        arrayAdapterDate = ArrayAdapter(this,android.R.layout.simple_list_item_1,dateArray)
        dateListView.adapter = arrayAdapterDate

        arrayAdapterName = ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray)
        nameListView.adapter = arrayAdapterName

        arrayAdapterSiteBranch = ArrayAdapter(this,android.R.layout.simple_list_item_1,officeSiteArray)
        siteBranchListView.adapter = arrayAdapterSiteBranch

        arrayAdapterDepartment = ArrayAdapter(this,android.R.layout.simple_list_item_1,departmentArray)
        departmentListView.adapter = arrayAdapterDepartment

        arrayAdapterJobTitle = ArrayAdapter(this,android.R.layout.simple_list_item_1,jobTitleArray)
        jobTitleListView.adapter = arrayAdapterJobTitle

        arrayAdapterTimeIn = ArrayAdapter(this,android.R.layout.simple_list_item_1,timeInArray)
        timeInListView.adapter = arrayAdapterTimeIn

        arrayAdapterTimeOut = ArrayAdapter(this,android.R.layout.simple_list_item_1,timeOutArray)
        timeOutListView.adapter = arrayAdapterTimeOut

        arrayAdapterTotalTimeWorked = ArrayAdapter(this,android.R.layout.simple_list_item_1,timeWorkedArray)
        totalTimeWorkedListView.adapter = arrayAdapterTotalTimeWorked

    }
}