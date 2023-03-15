package com.kronos.timecardapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class LeaveScheduleView2Activity : AppCompatActivity() {
    private lateinit var startDate1: LocalDate
    private lateinit var endDate1: LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leavescheduleview2)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        val startDateRange = bundle!!.getString("StartDate").toString()
        val endDateRange = bundle.getString("EndDate").toString()
        val nameFilter = bundle.getString("FullName").toString()

        if(startDateRange.isNotEmpty() && endDateRange.isNotEmpty()){
            val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            startDate1 = LocalDate.parse(startDateRange,dtf) as LocalDate
            endDate1 = LocalDate.parse(endDateRange,dtf) as LocalDate

            val recyclerViewLeaveSchedule = findViewById<RecyclerView>(R.id.recyclerViewLeaveSchedule)
            recyclerViewLeaveSchedule.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel3>()

            var name = "NAME"
            var leave = "LEAVE"
            var startDate = "START DATE"
            var endDate = "RETURN DATE"
            var noOfDays= "NO OF DAYS"
            var reliever = "RELIEVER"
            var authorizedBy= "AUTHORIZED BY"

            data.add(ItemViewModel3(name, leave, startDate, endDate, noOfDays, reliever, authorizedBy))

            val db = DBHelper3(this,null)
            val cursor = db.getDetails()

            if(cursor!!.moveToFirst()){
                do{
                    val dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())

                    val startDatePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.DATE_FROM))
                    val testDate = LocalDate.parse(startDatePrint,dtf2)

                    val endDatePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.DATE_TO))
                    val testDate1 = LocalDate.parse(endDatePrint,dtf2)

                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.NAME_COL))
                    val leavePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.LEAVE_TYPE))
                    val noOfDaysPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.NO_OF_DAYS))
                    val relieverPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.RELIEVER_NAME))
                    val authorizedByPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.AUTHORIZED_BY))

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty()) && (testDate.isEqual(startDate1) || testDate.isAfter(startDate1)) && (testDate1.isEqual(endDate1) || testDate1.isBefore(endDate1))){
                        name = namePrint.toString()
                        leave = leavePrint.toString()
                        startDate = startDatePrint.toString()
                        endDate = endDatePrint.toString()
                        noOfDays = noOfDaysPrint.toString()
                        reliever = relieverPrint.toString()
                        authorizedBy = authorizedByPrint.toString()

                        data.add(ItemViewModel3(name, leave, startDate, endDate, noOfDays, reliever, authorizedBy))

                    }
                } while(cursor.moveToNext())
            }
            cursor.close()

            val adapter = CustomAdapter3(data)
            recyclerViewLeaveSchedule.adapter = adapter
        } else if(startDateRange.isEmpty() && endDateRange.isEmpty()){
            val recyclerViewLeaveSchedule = findViewById<RecyclerView>(R.id.recyclerViewLeaveSchedule)
            recyclerViewLeaveSchedule.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel3>()

            var name = "NAME"
            var leave = "LEAVE"
            var startDate = "START DATE"
            var endDate = "RETURN DATE"
            var noOfDays= "NO OF DAYS"
            var reliever = "RELIEVER"
            var authorizedBy= "AUTHORIZED BY"

            data.add(ItemViewModel3(name, leave, startDate, endDate, noOfDays, reliever, authorizedBy))

            val db = DBHelper3(this,null)
            val cursor = db.getDetails()

            if(cursor!!.moveToFirst()){
                do{
                    val startDatePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.DATE_FROM))
                    val endDatePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.DATE_TO))
                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.NAME_COL))
                    val leavePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.LEAVE_TYPE))
                    val noOfDaysPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.NO_OF_DAYS))
                    val relieverPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.RELIEVER_NAME))
                    val authorizedByPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper3.AUTHORIZED_BY))

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty()) && startDateRange.isEmpty() && endDateRange.isEmpty()){
                        name = namePrint.toString()
                        leave = leavePrint.toString()
                        startDate = startDatePrint.toString()
                        endDate = endDatePrint.toString()
                        noOfDays = noOfDaysPrint.toString()
                        reliever = relieverPrint.toString()
                        authorizedBy = authorizedByPrint.toString()

                        data.add(ItemViewModel3(name, leave, startDate, endDate, noOfDays, reliever, authorizedBy))

                    }
                } while(cursor.moveToNext())
            }
            cursor.close()

            val adapter = CustomAdapter3(data)
            recyclerViewLeaveSchedule.adapter = adapter
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}