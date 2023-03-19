package com.kronos.timecardapp

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.graphics.*
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TimeAttendanceViewActivity : AppCompatActivity() {
    private var pageHeight = 1120
    private var pageWidth = 792
    private lateinit var bmp: Bitmap
    private lateinit var scaledbmp: Bitmap
    private var PERMISSION_CODE = 101
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.timeattendanceview_option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemViewGeneratePDF -> {
                bmp = ContextCompat.getDrawable(this,R.drawable.time)?.toBitmap()!!
                scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)

                if(checkPermissions()){
                    Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show()
                } else{
                    requestPermission()
                }
                generatePDF()
                return true
            }
            R.id.itemViewPrint -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun generatePDF(){
        val pdfDocument: android.graphics.pdf.PdfDocument = android.graphics.pdf.PdfDocument()

        val paint = Paint()
        val title = Paint()

        val myPageInfo: android.graphics.pdf.PdfDocument.PageInfo? = android.graphics.pdf.PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val myPage: android.graphics.pdf.PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        val canvas: Canvas = myPage.canvas
        canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        title.textSize = 15F
        title.color = ContextCompat.getColor(this, R.color.purple_200)
        canvas.drawText("TIME ATTENDANCE SHEET", 209F, 100F, title)
        canvas.drawText("COMPANY NAME", 209F, 80F, title)

        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        title.color = ContextCompat.getColor(this, R.color.purple_200)
        title.textSize = 15F
        title.textAlign = Paint.Align.CENTER
        canvas.drawText("THIS IS A SAMPLE DOCUMENT", 396F, 560F, title)

        pdfDocument.finishPage(myPage)

        val file = File(Environment.getExternalStorageDirectory(),"GFG.pdf")

        try{
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(applicationContext,"PDF FILE GENERATED",Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(applicationContext,"FAILED TO GENERATE PDF FILE",Toast.LENGTH_SHORT).show()
        }
        pdfDocument.close()
    }
    private fun checkPermissions(): Boolean{
        val writeStoragePermission = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val readStoragePermission = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)

        return writeStoragePermission == PackageManager.PERMISSION_GRANTED && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_CODE){
            if(grantResults.isNotEmpty()){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this,"PERMISSION DENIED",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

