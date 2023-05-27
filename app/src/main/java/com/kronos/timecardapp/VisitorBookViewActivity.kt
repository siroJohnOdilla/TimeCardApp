package com.kronos.timecardapp

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class VisitorBookViewActivity : AppCompatActivity() {
    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitorbookview)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        val startDateRange = intent.getStringExtra("StartDate").toString()
        val endDateRange = intent.getStringExtra("EndDate").toString()
        val nameFilter = intent.getStringExtra("FullName").toString()

        if(startDateRange.isNotEmpty() && endDateRange.isNotEmpty()){
            val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            startDate = LocalDate.parse(startDateRange,dtf) as LocalDate
            endDate = LocalDate.parse(endDateRange,dtf) as LocalDate

            val recyclerViewVisitorBook = findViewById<RecyclerView>(R.id.recyclerViewVisitorBook)
            recyclerViewVisitorBook.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel5>()

            var date = "DATE"
            var name = "NAME"
            var nationalId = "NATIONAL ID"
            var telephoneNumber = "TELEPHONE NO."
            var company = "COMPANY"
            var companyHost = "CONTACT PERSON"
            var reasonVisit = "REASON FOR VISIT"
            var timeIn = "TIME IN"
            var timeOut = "TIME OUT"

            //data.add(ItemViewModel5(date, name, nationalId, telephoneNumber, company, companyHost, reasonVisit, timeIn, timeOut))

            val db = DBHelper4(this,null)
            val cursor = db.getDetails()

            if(cursor!!.moveToLast()){
                do{
                    val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.DATE_COL))
                    val dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
                    val testDate = LocalDate.parse(datePrint,dtf2)

                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))
                    val nationalIdPrint= cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NATIONAL_ID_NO))
                    val telephoneNumberPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TELEPHONE_N0))
                    val companyPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.COMPANY_NAME))
                    val companyHostPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.CONTACT_PERSON))
                    val reasonVisitPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.REASON_FOR_VISIT))
                    val timeInPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TIME_IN))
                    val timeOutPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TIME_OUT))

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty()) && (testDate.isAfter(startDate) || testDate.isEqual(startDate)) && (testDate.isBefore(endDate) || testDate.isEqual(endDate))){
                        date = datePrint.toString()
                        name = namePrint.toString()
                        nationalId = nationalIdPrint.toString()
                        telephoneNumber = telephoneNumberPrint.toString()
                        company = companyPrint.toString()
                        companyHost = companyHostPrint.toString()
                        reasonVisit = reasonVisitPrint.toString()
                        timeIn = timeInPrint.toString()
                        timeOut = timeOutPrint.toString()

                        data.add(ItemViewModel5(date, name, nationalId, telephoneNumber, company, companyHost, reasonVisit, timeIn, timeOut))
                    }
                } while(cursor.moveToPrevious())
            }
            cursor.close()

            val adapter = CustomAdapter5(data)
            recyclerViewVisitorBook.adapter = adapter

            adapter.setOnClickListener(object: CustomAdapter5.onItemClickListener{
                override fun onItemClick(position: Int) {

                }
            })

        } else if(startDateRange.isEmpty() && endDateRange.isEmpty()){

            val recyclerViewVisitorBook = findViewById<RecyclerView>(R.id.recyclerViewVisitorBook)
            recyclerViewVisitorBook.layoutManager = LinearLayoutManager(this)

            val data = ArrayList<ItemViewModel5>()

            var date = "DATE"
            var name = "NAME"
            var nationalId = "NATIONAL ID NO."
            var telephoneNumber = "TELEPHONE NO."
            var company = "COMPANY"
            var companyHost = "CONTACT PERSON"
            var reasonVisit = "REASON FOR VISIT"
            var timeIn = "TIME IN"
            var timeOut = "TIME OUT"

            //data.add(ItemViewModel5(date, name, nationalId, telephoneNumber, company, companyHost, reasonVisit, timeIn, timeOut))

            val db = DBHelper4(this,null)
            val cursor = db.getDetails()

            if(cursor!!.moveToLast()){
                do{
                    val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.DATE_COL))
                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))
                    val nationalIdPrint= cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NATIONAL_ID_NO))
                    val telephoneNumberPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TELEPHONE_N0))
                    val companyPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.COMPANY_NAME))
                    val companyHostPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.CONTACT_PERSON))
                    val reasonVisitPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.REASON_FOR_VISIT))
                    val timeInPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TIME_IN))
                    val timeOutPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.TIME_OUT))

                    if((nameFilter == namePrint.toString() || nameFilter.isEmpty())){
                        date = datePrint.toString()
                        name = namePrint.toString()
                        nationalId = nationalIdPrint.toString()
                        telephoneNumber = telephoneNumberPrint.toString()
                        company = companyPrint.toString()
                        companyHost = companyHostPrint.toString()
                        reasonVisit = reasonVisitPrint.toString()
                        timeIn = timeInPrint.toString()
                        timeOut = timeOutPrint.toString()

                        data.add(ItemViewModel5(date, name, nationalId, telephoneNumber, company, companyHost, reasonVisit, timeIn, timeOut))
                    }
                } while(cursor.moveToPrevious())
            }
            cursor.close()

            val adapter = CustomAdapter5(data)
            recyclerViewVisitorBook.adapter = adapter

            adapter.setOnClickListener(object: CustomAdapter5.onItemClickListener{
                override fun onItemClick(position: Int) {

                }
            })
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.timeattendanceview_option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemViewGeneratePDF -> {
                return true
            }
            R.id.itemViewPrint -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}