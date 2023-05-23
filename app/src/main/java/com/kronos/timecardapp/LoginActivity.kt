package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs
class LoginActivity : AppCompatActivity(){
    private lateinit var editTxtFullNameNationalIdLogIn: AutoCompleteTextView
    private lateinit var editTxtPINLogIn: EditText
    private lateinit var txtDisplayCurrentDay: TextView
    private lateinit var txtDisplayCurrentDate: TextView
    private lateinit var btnClockIn: Button
    private lateinit var btnClockOut: Button
    private lateinit var btnCreateAccount: Button
    private lateinit var employeeList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        txtDisplayCurrentDay = findViewById(R.id.txtDisplayCurrentDay)
        val dayDisplay = SimpleDateFormat("EEEE", Locale.getDefault())
        val getCurrentDay = dayDisplay.format(Date())
        txtDisplayCurrentDay.text = getCurrentDay

        txtDisplayCurrentDate = findViewById(R.id.txtDisplayCurrentDate)
        val dateDisplay = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val getCurrentDate = dateDisplay.format(Date())
        txtDisplayCurrentDate.text = getCurrentDate

        btnClockIn = findViewById(R.id.btnClockIn)
        btnClockIn.setOnClickListener {
            if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"LOGIN DETAILS REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isNotEmpty()){
                Toast.makeText(this,"FULL NAME/NATIONAL ID NO. REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isNotEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"PIN REQUIRED",Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this,null)
                val cursor = db.getLoginDetails()

                if (cursor!!.moveToFirst()){
                    do{
                        val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val nationalIdLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                        val pinLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))
                        val officeSiteBranchLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                        val departmentLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                        val jobTitleLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                        val companyNameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                        val accountTagLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                        if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK IN",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK IN",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK IN",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK IN",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()

                            val makeDateFormat1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val checkDate = makeDateFormat1.format(Date())

                            val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            val checkTime = makeDateFormat2.format(Date())

                            val db = DBHelper2(this,null)
                            val cursor = db.getDetails()

                            if(cursor!!.moveToFirst()){
                                do {
                                    val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                                    val dateCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                                    val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))

                                    if (displayAccountName.toString() == nameCheck.toString() && checkDate.toString() == dateCheck.toString() && checkTime.toString() != timeInCheck.toString()){
                                        val nameToThrow = nameCheck.toString()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        Toast.makeText(this,"$nameToThrow\nHAS ALREADY CLOCKED IN", Toast.LENGTH_SHORT).show()
                                        break
                                    }
                                } while (cursor.moveToNext())
                            }
                            cursor.close()

                            val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val date = makeDateFormat.format(Date())

                            val name = displayAccountName.toString()
                            val jobTitle = displayJobTitle.toString()
                            val department = displayDepartment.toString()
                            val officeBranchSite = displayOfficeSiteBranch.toString()

                            val makeTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            val timeIn = makeTimeFormat.format(Date())

                            val timeOut = "to be set"
                            val totalTimeWorked = "to be set"

                            db.addDetails(date, name, jobTitle, department, officeBranchSite, timeIn, timeOut, totalTimeWorked)

                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            val dialog = BottomSheetDialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.bottomsheet_confirmattendance)

                            val attendanceMessage = dialog.findViewById<TextView>(R.id.attendanceMessage)
                            val displayMessage = "CLOCK IN SUCCESSFUL!"
                            if (attendanceMessage != null) {
                                attendanceMessage.text = displayMessage
                            }
                            val txtAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtAttendanceConfirm)
                            if (txtAttendanceConfirm != null) {
                                val displayName = "Name:\n$displayAccountName"
                                txtAttendanceConfirm.text = displayName
                            }
                            val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                            if (txtTimeAttendanceConfirm != null) {
                                val displayTime = "Time in:\n$timeIn"
                                txtTimeAttendanceConfirm.text = displayTime
                            }
                            val btnOkAttendanceConfirm = dialog.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                            if (btnOkAttendanceConfirm != null) {
                                btnOkAttendanceConfirm.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                            dialog.show()
                            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog.window!!.setGravity(Gravity.BOTTOM)

                        } else if (editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()

                            val makeDateFormat1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val checkDate = makeDateFormat1.format(Date())

                            val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            val checkTime = makeDateFormat2.format(Date())

                            val db = DBHelper2(this,null)
                            val cursor = db.getDetails()

                            if(cursor!!.moveToFirst()){
                                do {
                                    val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                                    val dateCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                                    val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))

                                    if (displayAccountName.toString() == nameCheck.toString() && checkDate.toString() == dateCheck.toString() && checkTime.toString() != timeInCheck.toString()){
                                        val nameToThrow = nameCheck.toString()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        Toast.makeText(this,"$nameToThrow\nHAS ALREADY CLOCKED IN", Toast.LENGTH_SHORT).show()
                                        break
                                    }
                                } while (cursor.moveToNext())
                            }
                            cursor.close()

                            val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val date = makeDateFormat.format(Date())

                            val name = displayAccountName.toString()
                            val jobTitle = displayJobTitle.toString()
                            val department = displayDepartment.toString()
                            val officeBranchSite = displayOfficeSiteBranch.toString()

                            val makeTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            val timeIn = makeTimeFormat.format(Date())

                            val timeOut = "to be set"
                            val totalTimeWorked = "to be set"

                            db.addDetails(date, name, jobTitle, department, officeBranchSite, timeIn, timeOut, totalTimeWorked)

                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            val dialog = BottomSheetDialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.bottomsheet_confirmattendance)

                            val attendanceMessage = dialog.findViewById<TextView>(R.id.attendanceMessage)
                            val displayMessage = "CLOCK IN SUCCESSFUL!"
                            if (attendanceMessage != null) {
                                attendanceMessage.text = displayMessage
                            }
                            val txtAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtAttendanceConfirm)
                            if (txtAttendanceConfirm != null) {
                                val displayName = "Name:\n$displayAccountName"
                                txtAttendanceConfirm.text = displayName
                            }
                            val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                            if (txtTimeAttendanceConfirm != null) {
                                val displayTime = "Time in:\n$timeIn"
                                txtTimeAttendanceConfirm.text = displayTime
                            }
                            val btnOkAttendanceConfirm = dialog.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                            if (btnOkAttendanceConfirm != null) {
                                btnOkAttendanceConfirm.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                            dialog.show()
                            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog.window!!.setGravity(Gravity.BOTTOM)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnClockOut = findViewById(R.id.btnClockOut)
        btnClockOut.setOnClickListener {
            if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"LOGIN DETAILS REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isNotEmpty()){
                Toast.makeText(this,"FULL NAME/NATIONAL ID NO. REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isNotEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"PIN REQUIRED",Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this,null)
                val cursor = db.getLoginDetails()

                if (cursor!!.moveToFirst()){
                    do{
                        val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val nationalIdLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                        val pinLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))
                        val companyNameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                        val accountTagLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                        if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK OUT",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK OUT",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK OUT",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            Toast.makeText(this,"UNAUTHORIZED CLOCK OUT",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()

                            val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val dateCheck = makeDateFormat.format(Date())

                            val db = DBHelper2(this, null)
                            val cursor = db.getDetails()

                            if (cursor!!.moveToFirst()) {
                                do {
                                    val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                                    val checkDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                                    val jobTitleCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.JOB_TITLE_COL))
                                    val departmentCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DEPARTMENT_COL))
                                    val officeBranchSiteCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.OFFICE_SITE_COL))
                                    val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                                    val timeOutCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                                    val totalTimeWorkedCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TOTAL_TIME_WORKED_COL))
                                    val idCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.ID_COL))

                                    if (displayAccountName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && timeOutCheck.toString() == "to be set") {
                                        val id = idCheck.toLong()

                                        val date1 = checkDate.toString()
                                        val name1 = nameCheck.toString()
                                        val jobTitle1 = jobTitleCheck.toString()
                                        val department1 = departmentCheck.toString()
                                        val officeBranchSite1 = officeBranchSiteCheck.toString()
                                        val timeIn1 = timeInCheck.toString()

                                        val makeTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                        val timeOut1 = makeTimeFormat.format(Date())

                                        val d1: Date = makeTimeFormat.parse(timeInCheck) as Date
                                        val d2: Date = makeTimeFormat.parse(timeOut1) as Date

                                        val difference: Long = abs(d2.time - d1.time)
                                        val differenceHours = (difference / (1000 * 60 * 60)) % 24
                                        val differenceMinutes = (difference / (1000 * 60)) % 60
                                        val differenceSeconds = (difference / 1000) % 60

                                        val totalTimeWorked1 = ("$differenceHours HRS $differenceMinutes MIN $differenceSeconds SEC")

                                        db.updateDetails(id, date1, name1, jobTitle1, department1, officeBranchSite1, timeIn1, timeOut1, totalTimeWorked1)
                                        db.close()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        val dialog = BottomSheetDialog(this)
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                        dialog.setContentView(R.layout.bottomsheet_confirmattendance)

                                        val attendanceMessage = dialog.findViewById<TextView>(R.id.attendanceMessage)
                                        val displayMessage = "CLOCK OUT SUCCESSFUL!"
                                        if (attendanceMessage != null) {
                                            attendanceMessage.text = displayMessage
                                        }
                                        val txtAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtAttendanceConfirm)
                                        if (txtAttendanceConfirm != null) {
                                            val displayName = "Name:\n$displayAccountName"
                                            txtAttendanceConfirm.text = displayName
                                        }
                                        val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                        if (txtTimeAttendanceConfirm != null) {
                                            val display = "Time out: $timeOut1\nTotal Time: $totalTimeWorked1"
                                            txtTimeAttendanceConfirm.text = display
                                        }
                                        val btnOkAttendanceConfirm = dialog.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                        if (btnOkAttendanceConfirm != null) {
                                            btnOkAttendanceConfirm.setOnClickListener {
                                                dialog.dismiss()
                                            }
                                        }
                                        dialog.show()
                                        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                        dialog.window!!.setGravity(Gravity.BOTTOM)

                                    } else if (displayAccountName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && totalTimeWorkedCheck.toString() != "to be set") {
                                        val nameToThrow = nameCheck.toString()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        Toast.makeText(this,"$nameToThrow\nHAS ALREADY CLOCKED OUT", Toast.LENGTH_SHORT).show()
                                        break
                                    }
                                } while (cursor.moveToNext())
                            }
                            cursor.close()
                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()

                            val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val dateCheck = makeDateFormat.format(Date())

                            val db = DBHelper2(this, null)
                            val cursor = db.getDetails()

                            if (cursor!!.moveToFirst()) {
                                do {
                                    val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.NAME_COL))
                                    val checkDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DATE_COL))
                                    val jobTitleCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.JOB_TITLE_COL))
                                    val departmentCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.DEPARTMENT_COL))
                                    val officeBranchSiteCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.OFFICE_SITE_COL))
                                    val timeInCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_IN_COL))
                                    val timeOutCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TIME_OUT_COL))
                                    val totalTimeWorkedCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.TOTAL_TIME_WORKED_COL))
                                    val idCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper2.ID_COL))

                                    if (displayAccountName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && timeOutCheck.toString() == "to be set") {
                                        val id = idCheck.toLong()

                                        val date1 = checkDate.toString()
                                        val name1 = nameCheck.toString()
                                        val jobTitle1 = jobTitleCheck.toString()
                                        val department1 = departmentCheck.toString()
                                        val officeBranchSite1 = officeBranchSiteCheck.toString()
                                        val timeIn1 = timeInCheck.toString()

                                        val makeTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                        val timeOut1 = makeTimeFormat.format(Date())

                                        val d1: Date = makeTimeFormat.parse(timeInCheck) as Date
                                        val d2: Date = makeTimeFormat.parse(timeOut1) as Date

                                        val difference: Long = abs(d2.time - d1.time)
                                        val differenceHours = (difference / (1000 * 60 * 60)) % 24
                                        val differenceMinutes = (difference / (1000 * 60)) % 60
                                        val differenceSeconds = (difference / 1000) % 60

                                        val totalTimeWorked1 = ("$differenceHours HRS $differenceMinutes MIN $differenceSeconds SEC")

                                        db.updateDetails(id, date1, name1, jobTitle1, department1, officeBranchSite1, timeIn1, timeOut1, totalTimeWorked1)
                                        db.close()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        val dialog = BottomSheetDialog(this)
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                        dialog.setContentView(R.layout.bottomsheet_confirmattendance)

                                        val attendanceMessage = dialog.findViewById<TextView>(R.id.attendanceMessage)
                                        val displayMessage = "CLOCK OUT SUCCESSFUL!"
                                        if (attendanceMessage != null) {
                                            attendanceMessage.text = displayMessage
                                        }
                                        val txtAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtAttendanceConfirm)
                                        if (txtAttendanceConfirm != null) {
                                            val displayName = "Name:\n$displayAccountName"
                                            txtAttendanceConfirm.text = displayName
                                        }
                                        val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                        if (txtTimeAttendanceConfirm != null) {
                                            val display = "Time out: $timeOut1\nTotal Time: $totalTimeWorked1"
                                            txtTimeAttendanceConfirm.text = display
                                        }
                                        val btnOkAttendanceConfirm = dialog.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                        if (btnOkAttendanceConfirm != null) {
                                            btnOkAttendanceConfirm.setOnClickListener {
                                                dialog.dismiss()
                                            }
                                        }
                                        dialog.show()
                                        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                        dialog.window!!.setGravity(Gravity.BOTTOM)

                                    } else if (displayAccountName.toString() == nameCheck.toString() && dateCheck.toString() == checkDate.toString() && totalTimeWorkedCheck.toString() != "to be set") {
                                        val nameToThrow = nameCheck.toString()

                                        editTxtFullNameNationalIdLogIn.text.clear()
                                        editTxtPINLogIn.text.clear()

                                        Toast.makeText(this,"$nameToThrow\nHAS ALREADY CLOCKED OUT", Toast.LENGTH_SHORT).show()
                                        break
                                    }
                                } while (cursor.moveToNext())
                            }
                            cursor.close()
                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }

        employeeList = ArrayList()

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if(nameCheck.toString().isNotEmpty()){
                    val name = nameCheck.toString()
                    employeeList.add(name)
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, employeeList)

        editTxtFullNameNationalIdLogIn = findViewById(R.id.editTxtFullNameNationalIdLogIn)
        editTxtFullNameNationalIdLogIn.setAdapter(adapter)

        editTxtPINLogIn = findViewById(R.id.editTxtPINLogIn)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"LOGIN DETAILS REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isEmpty() && editTxtPINLogIn.text.toString().trim().isNotEmpty()){
                Toast.makeText(this,"FULL NAME/NATIONAL ID NO. REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().isNotEmpty() && editTxtPINLogIn.text.toString().trim().isEmpty()){
                Toast.makeText(this,"PIN REQUIRED",Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this,null)
                val cursor = db.getLoginDetails()

                if (cursor!!.moveToFirst()){
                    do{
                        val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val nationalIdLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                        val pinLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))
                        val officeSiteBranchLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                        val departmentLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                        val jobTitleLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                        val companyNameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                        val accountTagLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                        if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val dialog = BottomSheetDialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.bottomsheet_createcompany)

                            val editTxtCompanyNameCreate = dialog.findViewById<EditText>(R.id.editTxtCompanyNameCreate)
                            val editTxtCompanyNameInitials = dialog.findViewById<EditText>(R.id.editTxtCompanyNameInitials)
                            val editTxtCompanyAdmissionKey = dialog.findViewById<EditText>(R.id.editTxtCompanyAdmissionKey)

                            val btnCancelCompany = dialog.findViewById<ImageView>(R.id.btnCancelCompany)
                            if (btnCancelCompany != null) {
                                btnCancelCompany.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }

                            val btnFinishCompanyNameRegistration = dialog.findViewById<Button>(R.id.btnFinishCompanyNameRegistration)
                            if (btnFinishCompanyNameRegistration != null) {
                                btnFinishCompanyNameRegistration.setOnClickListener {
                                    val intent = Intent(this,HomePageDrawerActivity::class.java)
                                    if (editTxtCompanyNameCreate != null) {
                                        if(editTxtCompanyNameCreate.text.toString().trim().isEmpty()){
                                            Toast.makeText(this,"COMPANY NAME REQUIRED",Toast.LENGTH_SHORT).show()
                                        } else if (editTxtCompanyNameInitials != null) {
                                            if (editTxtCompanyNameInitials.text.toString().trim().isEmpty()){
                                                Toast.makeText(this,"COMPANY INITIALS REQUIRED",Toast.LENGTH_SHORT).show()
                                            } else if (editTxtCompanyAdmissionKey != null) {
                                                if (editTxtCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                                    Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED",Toast.LENGTH_SHORT).show()
                                                } else{
                                                    val company = editTxtCompanyNameCreate.text.toString().trim().uppercase()
                                                    val companyInitials = editTxtCompanyNameInitials.text.toString().trim().uppercase()
                                                    val companyAdmissionKey = editTxtCompanyAdmissionKey.text.toString().trim().uppercase()

                                                    val db = DBHelper(this,null)
                                                    val cursor = db.getLoginDetails()

                                                    if (cursor!!.moveToFirst()){
                                                        do{
                                                            val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                            val idLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                            if (displayAccountName.toString() == nameLogIn.toString()) {
                                                                val id = idLogIn.toLong()

                                                                db.updateCompany(id, company, companyInitials, companyAdmissionKey)
                                                                db.close()

                                                                intent.putExtra("nameLogInKey1", displayAccountName)
                                                                intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                intent.putExtra("displayCompanyNameKey1", company)

                                                                editTxtCompanyNameCreate.text.clear()
                                                                editTxtCompanyNameInitials.text.clear()
                                                                editTxtCompanyAdmissionKey.text.clear()
                                                                startActivity(intent)

                                                                Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                            }
                                                        } while(cursor.moveToNext())
                                                    }
                                                    cursor.close()
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            val btnJoinCompany2 = dialog.findViewById<Button>(R.id.btnJoinCompany2)
                            if (btnJoinCompany2 != null) {
                                btnJoinCompany2.setOnClickListener {
                                    val dialog1 = BottomSheetDialog(this)
                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                    dialog1.setContentView(R.layout.bottomsheet_joincompany)

                                    val editTxtJoinCompanyAdmissionKey = dialog1.findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKey)

                                    val btnCancelJoinCompany = dialog1.findViewById<ImageView>(R.id.btnCancelJoinCompany)
                                    if (btnCancelJoinCompany != null) {
                                        btnCancelJoinCompany.setOnClickListener {
                                            dialog1.dismiss()
                                        }
                                    }

                                    val btnSubmitAdmissionKey = dialog1.findViewById<Button>(R.id.btnSubmitAdmissionKey)
                                    if (btnSubmitAdmissionKey != null) {
                                        btnSubmitAdmissionKey.setOnClickListener {
                                            val intent = Intent(this,HomePageDrawerActivity::class.java)
                                            if (editTxtJoinCompanyAdmissionKey != null) {
                                                if(editTxtJoinCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                                    Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    val companyAdmissionKey = editTxtJoinCompanyAdmissionKey.text.toString().trim().uppercase()

                                                    val db = DBHelper(this, null)
                                                    val cursor = db.getLoginDetails()

                                                    if(cursor!!.moveToFirst()){
                                                        do{
                                                            val companyNameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                                                            val companyInitialsCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                                                            val companyAdmissionKeyCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_ADM_KEY))

                                                            if (companyAdmissionKeyCheck.toString() == companyAdmissionKey){
                                                                val companyNameUser = companyNameCheck.toString()
                                                                val companyInitialsUser = companyInitialsCheck.toString()

                                                                if (cursor.moveToFirst()){
                                                                    do{
                                                                        val nameLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                                        val idLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                                        if(displayAccountName.toString() == nameLogin.toString()){
                                                                            val id = idLogin.toLong()

                                                                            db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKey)
                                                                            db.close()

                                                                            intent.putExtra("nameLogInKey1", displayAccountName)
                                                                            intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                            intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                            intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                            intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                            intent.putExtra("displayCompanyNameKey1", companyNameUser)

                                                                            editTxtJoinCompanyAdmissionKey.text.clear()
                                                                            startActivity(intent)

                                                                            Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                                        }
                                                                    } while(cursor.moveToNext())
                                                                }
                                                                cursor.close()
                                                            }
                                                        } while (cursor.moveToNext())
                                                    }
                                                    cursor.close()
                                                }
                                            }
                                        }
                                    }
                                    dialog1.show()
                                    dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                    dialog1.window!!.setGravity(Gravity.BOTTOM)
                                }
                            }
                            dialog.show()
                            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog.window!!.setGravity(Gravity.BOTTOM)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val dialog = BottomSheetDialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.bottomsheet_createcompany)

                            val editTxtCompanyNameCreate = dialog.findViewById<EditText>(R.id.editTxtCompanyNameCreate)
                            val editTxtCompanyNameInitials = dialog.findViewById<EditText>(R.id.editTxtCompanyNameInitials)
                            val editTxtCompanyAdmissionKey = dialog.findViewById<EditText>(R.id.editTxtCompanyAdmissionKey)

                            val btnCancelCompany = dialog.findViewById<ImageView>(R.id.btnCancelCompany)
                            if (btnCancelCompany != null) {
                                btnCancelCompany.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }

                            val btnFinishCompanyNameRegistration = dialog.findViewById<Button>(R.id.btnFinishCompanyNameRegistration)
                            if (btnFinishCompanyNameRegistration != null) {
                                btnFinishCompanyNameRegistration.setOnClickListener {
                                    val intent = Intent(this,HomePageDrawerActivity::class.java)
                                    if (editTxtCompanyNameCreate != null) {
                                        if(editTxtCompanyNameCreate.text.toString().trim().isEmpty()){
                                            Toast.makeText(this,"COMPANY NAME REQUIRED",Toast.LENGTH_SHORT).show()
                                        } else if (editTxtCompanyNameInitials != null) {
                                            if (editTxtCompanyNameInitials.text.toString().trim().isEmpty()){
                                                Toast.makeText(this,"COMPANY INITIALS REQUIRED",Toast.LENGTH_SHORT).show()
                                            } else if (editTxtCompanyAdmissionKey != null) {
                                                if (editTxtCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                                    Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED",Toast.LENGTH_SHORT).show()
                                                } else{
                                                    val company = editTxtCompanyNameCreate.text.toString().trim().uppercase()
                                                    val companyInitials = editTxtCompanyNameInitials.text.toString().trim().uppercase()
                                                    val companyAdmissionKey = editTxtCompanyAdmissionKey.text.toString().trim().uppercase()

                                                    val db = DBHelper(this,null)
                                                    val cursor = db.getLoginDetails()

                                                    if (cursor!!.moveToFirst()){
                                                        do{
                                                            val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                            val idLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                            if (displayAccountName.toString() == nameLogIn.toString()) {
                                                                val id = idLogIn.toLong()

                                                                db.updateCompany(id, company, companyInitials, companyAdmissionKey)
                                                                db.close()

                                                                intent.putExtra("nameLogInKey1", displayAccountName)
                                                                intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                intent.putExtra("displayCompanyNameKey1", company)

                                                                editTxtCompanyNameCreate.text.clear()
                                                                editTxtCompanyNameInitials.text.clear()
                                                                editTxtCompanyAdmissionKey.text.clear()
                                                                startActivity(intent)

                                                                Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                            }
                                                        } while(cursor.moveToNext())
                                                    }
                                                    cursor.close()
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            val btnJoinCompany2 = dialog.findViewById<Button>(R.id.btnJoinCompany2)
                            if (btnJoinCompany2 != null) {
                                btnJoinCompany2.setOnClickListener {
                                    val dialog1 = BottomSheetDialog(this)
                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                    dialog1.setContentView(R.layout.bottomsheet_joincompany)

                                    val editTxtJoinCompanyAdmissionKey = dialog1.findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKey)

                                    val btnCancelJoinCompany = dialog1.findViewById<ImageView>(R.id.btnCancelJoinCompany)
                                    if (btnCancelJoinCompany != null) {
                                        btnCancelJoinCompany.setOnClickListener {
                                            dialog1.dismiss()
                                        }
                                    }

                                    val btnSubmitAdmissionKey = dialog1.findViewById<Button>(R.id.btnSubmitAdmissionKey)
                                    if (btnSubmitAdmissionKey != null) {
                                        btnSubmitAdmissionKey.setOnClickListener {
                                            val intent = Intent(this,HomePageDrawerActivity::class.java)
                                            if (editTxtJoinCompanyAdmissionKey != null) {
                                                if(editTxtJoinCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                                    Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    val companyAdmissionKey = editTxtJoinCompanyAdmissionKey.text.toString().trim().uppercase()

                                                    val db = DBHelper(this, null)
                                                    val cursor = db.getLoginDetails()

                                                    if(cursor!!.moveToFirst()){
                                                        do{
                                                            val companyNameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                                                            val companyInitialsCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                                                            val companyAdmissionKeyCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_ADM_KEY))

                                                            if (companyAdmissionKeyCheck.toString() == companyAdmissionKey){
                                                                val companyNameUser = companyNameCheck.toString()
                                                                val companyInitialsUser = companyInitialsCheck.toString()

                                                                if (cursor.moveToFirst()){
                                                                    do{
                                                                        val nameLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                                        val idLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                                        if(displayAccountName.toString() == nameLogin.toString()){
                                                                            val id = idLogin.toLong()

                                                                            db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKey)
                                                                            db.close()

                                                                            intent.putExtra("nameLogInKey1", displayAccountName)
                                                                            intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                            intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                            intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                            intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                            intent.putExtra("displayCompanyNameKey1", companyNameUser)

                                                                            editTxtJoinCompanyAdmissionKey.text.clear()
                                                                            startActivity(intent)

                                                                            Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                                        }
                                                                    } while(cursor.moveToNext())
                                                                }
                                                                cursor.close()
                                                            }
                                                        } while (cursor.moveToNext())
                                                    }
                                                    cursor.close()
                                                }
                                            }
                                        }
                                    }
                                    dialog1.show()
                                    dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                    dialog1.window!!.setGravity(Gravity.BOTTOM)
                                }
                            }
                            dialog.show()
                            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog.window!!.setGravity(Gravity.BOTTOM)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val dialog1 = BottomSheetDialog(this)
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog1.setContentView(R.layout.bottomsheet_joincompany)

                            val editTxtJoinCompanyAdmissionKey = dialog1.findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKey)

                            val btnCancelJoinCompany = dialog1.findViewById<ImageView>(R.id.btnCancelJoinCompany)
                            if (btnCancelJoinCompany != null) {
                                btnCancelJoinCompany.setOnClickListener {
                                    dialog1.dismiss()
                                }
                            }

                            val btnSubmitAdmissionKey = dialog1.findViewById<Button>(R.id.btnSubmitAdmissionKey)
                            if (btnSubmitAdmissionKey != null) {
                                btnSubmitAdmissionKey.setOnClickListener {
                                    val intent = Intent(this,HomePageDrawerActivity::class.java)
                                    if (editTxtJoinCompanyAdmissionKey != null) {
                                        if(editTxtJoinCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                            Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED", Toast.LENGTH_SHORT).show()
                                        } else {
                                            val companyAdmissionKey = editTxtJoinCompanyAdmissionKey.text.toString().trim().uppercase()

                                            val db = DBHelper(this, null)
                                            val cursor = db.getLoginDetails()

                                            if(cursor!!.moveToFirst()){
                                                do{
                                                    val companyNameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                                                    val companyInitialsCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                                                    val companyAdmissionKeyCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_ADM_KEY))

                                                    if (companyAdmissionKeyCheck.toString() == companyAdmissionKey){
                                                        val companyNameUser = companyNameCheck.toString()
                                                        val companyInitialsUser = companyInitialsCheck.toString()

                                                        if (cursor.moveToFirst()){
                                                            do{
                                                                val nameLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                                val idLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                                if(displayAccountName.toString() == nameLogin.toString()){
                                                                    val id = idLogin.toLong()

                                                                    db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKey)
                                                                    db.close()

                                                                    intent.putExtra("nameLogInKey1", displayAccountName)
                                                                    intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                    intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                    intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                    intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                    intent.putExtra("displayCompanyNameKey1", companyNameUser)

                                                                    editTxtJoinCompanyAdmissionKey.text.clear()
                                                                    startActivity(intent)

                                                                    Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                                }
                                                            } while(cursor.moveToNext())
                                                        }
                                                        cursor.close()
                                                    }
                                                } while (cursor.moveToNext())
                                            }
                                            cursor.close()
                                        }
                                    }
                                }
                            }
                            dialog1.show()
                            dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog1.window!!.setGravity(Gravity.BOTTOM)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val dialog1 = BottomSheetDialog(this)
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog1.setContentView(R.layout.bottomsheet_joincompany)

                            val editTxtJoinCompanyAdmissionKey = dialog1.findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKey)

                            val btnCancelJoinCompany = dialog1.findViewById<ImageView>(R.id.btnCancelJoinCompany)
                            if (btnCancelJoinCompany != null) {
                                btnCancelJoinCompany.setOnClickListener {
                                    dialog1.dismiss()
                                }
                            }

                            val btnSubmitAdmissionKey = dialog1.findViewById<Button>(R.id.btnSubmitAdmissionKey)
                            if (btnSubmitAdmissionKey != null) {
                                btnSubmitAdmissionKey.setOnClickListener {
                                    val intent = Intent(this,HomePageDrawerActivity::class.java)
                                    if (editTxtJoinCompanyAdmissionKey != null) {
                                        if(editTxtJoinCompanyAdmissionKey.text.toString().trim().isEmpty()){
                                            Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED", Toast.LENGTH_SHORT).show()
                                        } else {
                                            val companyAdmissionKey = editTxtJoinCompanyAdmissionKey.text.toString().trim().uppercase()

                                            val db = DBHelper(this, null)
                                            val cursor = db.getLoginDetails()

                                            if(cursor!!.moveToFirst()){
                                                do{
                                                    val companyNameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                                                    val companyInitialsCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                                                    val companyAdmissionKeyCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_ADM_KEY))

                                                    if (companyAdmissionKeyCheck.toString() == companyAdmissionKey){
                                                        val companyNameUser = companyNameCheck.toString()
                                                        val companyInitialsUser = companyInitialsCheck.toString()

                                                        if (cursor.moveToFirst()){
                                                            do{
                                                                val nameLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                                val idLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                                if(displayAccountName.toString() == nameLogin.toString()){
                                                                    val id = idLogin.toLong()

                                                                    db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKey)
                                                                    db.close()

                                                                    intent.putExtra("nameLogInKey1", displayAccountName)
                                                                    intent.putExtra("displayOfficeSiteBranchKey1", displayOfficeSiteBranch)
                                                                    intent.putExtra("displayDepartmentKey1", displayDepartment)
                                                                    intent.putExtra("displayJobTitleKey1", displayJobTitle)
                                                                    intent.putExtra("accountTagCheck1", accountTagCheck)
                                                                    intent.putExtra("displayCompanyNameKey1", companyNameUser)

                                                                    editTxtJoinCompanyAdmissionKey.text.clear()
                                                                    startActivity(intent)

                                                                    Toast.makeText(this, "COMPANY REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                                                                }
                                                            } while(cursor.moveToNext())
                                                        }
                                                        cursor.close()
                                                    }
                                                } while (cursor.moveToNext())
                                            }
                                            cursor.close()
                                        }
                                    }
                                }
                            }
                            dialog1.show()
                            dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog1.window!!.setGravity(Gravity.BOTTOM)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()
                            val displayCompanyName = companyNameLogIn.toString()

                            val intent = Intent(this,HomePageDrawerActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            intent.putExtra("displayCompanyNameKey1",displayCompanyName)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

                            Toast.makeText(this,"WELCOME\n$displayAccountName",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()
                            val displayCompanyName = companyNameLogIn.toString()

                            val intent = Intent(this,HomePageDrawerActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            intent.putExtra("displayCompanyNameKey1",displayCompanyName)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

                            Toast.makeText(this,"WELCOME\n$displayAccountName",Toast.LENGTH_SHORT).show()

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() != pinLogIn.toString()){
                            Toast.makeText(this,"INVALID PIN",Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        btnCreateAccount.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottomsheet_signupaccounttag)

            var displayAccountTag: String

            val editTxtAdminKey = dialog.findViewById<EditText>(R.id.editTxtAdminKey)

            val btnCancelAccountTag = dialog.findViewById<ImageView>(R.id.btnCancelAccountTag)
            if (btnCancelAccountTag != null) {
                btnCancelAccountTag.setOnClickListener{
                    dialog.dismiss()
                }
            }

            val btnAdminSignUp = dialog.findViewById<LinearLayout>(R.id.btnAdminSignUp)
            if (btnAdminSignUp != null) {
                btnAdminSignUp.setOnClickListener {
                    val adminKeyHolder = "K1LL-81LL-V0L12"

                    if (editTxtAdminKey != null) {
                        if(editTxtAdminKey.text.toString().trim().uppercase() == adminKeyHolder){
                            displayAccountTag = "ADMINISTRATOR"

                            val dialog1 = BottomSheetDialog(this)
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog1.setContentView(R.layout.bottomsheet_signupname)

                            val txtAccountTypeSignUp = dialog1.findViewById<TextView>(R.id.txtAccountTypeSignUp)
                            if (txtAccountTypeSignUp != null) {
                                txtAccountTypeSignUp.text = displayAccountTag
                            }
                            val accountTag1 = txtAccountTypeSignUp?.text.toString()

                            val editTxtFirstNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtFirstNameSignUp)
                            val editTxtMiddleNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtMiddleNameSignUp)
                            val editTxtLastNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtLastNameSignUp)

                            val btnCancelName = dialog1.findViewById<ImageView>(R.id.btnCancelName)
                            if (btnCancelName != null) {
                                btnCancelName.setOnClickListener{
                                    dialog1.dismiss()
                                }
                            }

                            val btnNextSignUpPersonalDetails = dialog1.findViewById<Button>(R.id.btnNextSignUpPersonalDetails)
                            if (btnNextSignUpPersonalDetails != null) {
                                btnNextSignUpPersonalDetails.setOnClickListener{
                                    if (editTxtFirstNameSignUp != null) {
                                        if(editTxtFirstNameSignUp.text.toString().trim().isEmpty()){
                                            Toast.makeText(this, "FIRST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                        } else if (editTxtLastNameSignUp != null) {
                                            if(editTxtLastNameSignUp.text.toString().trim().isEmpty()){
                                                Toast.makeText(this, "LAST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                            } else if (editTxtMiddleNameSignUp != null) {
                                                if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtMiddleNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()) {
                                                    val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtMiddleNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                                    val dialog2 = BottomSheetDialog(this)
                                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                    dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                                    val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                                    val adapter1 = ArrayAdapter(this, R.layout.dropdown_item, genders)

                                                    val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                                    if (spinnerGenderSignUp != null) {
                                                        spinnerGenderSignUp.setAdapter(adapter1)
                                                    }

                                                    val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                                    val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                                    if (editTxtDateOfBirthSignUp != null) {
                                                        editTxtDateOfBirthSignUp.setOnClickListener {
                                                            val c = Calendar.getInstance()

                                                            val year1 = c.get(Calendar. YEAR)
                                                            val month = c.get(Calendar. MONTH)
                                                            val day = c.get(Calendar. DAY_OF_MONTH)

                                                            val datePickerDialog = DatePickerDialog(
                                                                this,
                                                                { _, year, monthOfYear, dayOfMonth ->
                                                                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                                                                    editTxtDateOfBirthSignUp.setText(date)
                                                                },
                                                                year1,
                                                                month,
                                                                day
                                                            )
                                                            datePickerDialog.show()
                                                        }
                                                    }

                                                    val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                                    if (btnCancelPersonalDetails != null) {
                                                        btnCancelPersonalDetails.setOnClickListener{
                                                            dialog2.dismiss()
                                                        }
                                                    }

                                                    val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                                    if (btnNextSignUpJobDescription != null) {
                                                        btnNextSignUpJobDescription.setOnClickListener {
                                                            if (spinnerGenderSignUp != null) {
                                                                if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                                    Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                                } else if (editTxtNationalIDNo != null) {
                                                                    if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                        Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                                    } else if (editTxtDateOfBirthSignUp != null) {
                                                                        if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                            Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                        } else{
                                                                            val passGender = spinnerGenderSignUp.text.toString()
                                                                            val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                            val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                            val dialog3 = BottomSheetDialog(this)
                                                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                            dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                            val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                            val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                            val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                            val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                            if (btnCancelJobDescription != null) {
                                                                                btnCancelJobDescription.setOnClickListener{
                                                                                    dialog3.dismiss()
                                                                                }
                                                                            }

                                                                            val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                            if (btnNextSignUpContactInformation != null) {
                                                                                btnNextSignUpContactInformation.setOnClickListener {
                                                                                    if (editTxtOfficeBranchSignUp != null) {
                                                                                        if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(this,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else if (editTxtDepartmentSignUp != null) {
                                                                                            if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                                Toast.makeText(this,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                            } else if (editTxtJobTitleSignUp != null) {
                                                                                                if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                                    Toast.makeText(this,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                } else {
                                                                                                    val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                                    val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                                    val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                                    val dialog4 = BottomSheetDialog(this)
                                                                                                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                    dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                                    val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                                    val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                                    val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                                    if (btnCancelContactInformation != null) {
                                                                                                        btnCancelContactInformation.setOnClickListener{
                                                                                                            dialog4.dismiss()
                                                                                                        }
                                                                                                    }

                                                                                                    val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                                    if (btnNextSignUpSetPIN != null) {
                                                                                                        btnNextSignUpSetPIN.setOnClickListener {
                                                                                                            if (editTxtTelephoneNumberSignUp != null) {
                                                                                                                if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                                    Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                } else if (editTxtEmailAddressSignUp != null) {
                                                                                                                    if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                        Toast.makeText(this,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                    } else{
                                                                                                                        val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                        val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                        val dialog5 = BottomSheetDialog(this)
                                                                                                                        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                        dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                        val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                        val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                        val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                        if (btnCancelSetPin != null) {
                                                                                                                            btnCancelSetPin.setOnClickListener{
                                                                                                                                dialog5.dismiss()
                                                                                                                            }
                                                                                                                        }

                                                                                                                        val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                        if (btnFinishSignUp != null) {
                                                                                                                            btnFinishSignUp.setOnClickListener {
                                                                                                                                if (editTxtPINSignUp != null) {
                                                                                                                                    if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                        Toast.makeText(this,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                    } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                        if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                            Toast.makeText(this,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                        } else{
                                                                                                                                            val emailAddress = passEmailAddress
                                                                                                                                            val telephoneNumber = passTelephoneNumber
                                                                                                                                            val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                            val department = passDepartment
                                                                                                                                            val jobTitle = passJobTitle
                                                                                                                                            val gender = passGender
                                                                                                                                            val nationalId = passNationalId
                                                                                                                                            val dateOfBirth = passDateOfBirth
                                                                                                                                            val name = passName
                                                                                                                                            val accountTag = accountTag1
                                                                                                                                            val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                            val company = "to be set"
                                                                                                                                            val companyInitials = "to be set"
                                                                                                                                            val companyAdmissionKey = "to be set"

                                                                                                                                            val db = DBHelper(this, null)
                                                                                                                                            db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                            dialog.dismiss()
                                                                                                                                            dialog1.dismiss()
                                                                                                                                            dialog2.dismiss()
                                                                                                                                            dialog3.dismiss()
                                                                                                                                            dialog4.dismiss()
                                                                                                                                            dialog5.dismiss()

                                                                                                                                            val dialog6 = BottomSheetDialog(this)
                                                                                                                                            dialog6.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                                            dialog6.setContentView(R.layout.bottomsheet_confirmaccount)

                                                                                                                                            val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                            val displayName = "Name:\n$name"
                                                                                                                                            if (txtName != null) {
                                                                                                                                                txtName.text = displayName
                                                                                                                                            }

                                                                                                                                            val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                            val displayGender = "Gender:\n$gender"
                                                                                                                                            if (txtGender != null) {
                                                                                                                                                txtGender.text = displayGender
                                                                                                                                            }

                                                                                                                                            val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                            val displayNationalId = "National ID No.:\n$nationalId"
                                                                                                                                            if (txtNationalId != null) {
                                                                                                                                                txtNationalId.text = displayNationalId
                                                                                                                                            }

                                                                                                                                            val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                            val displayDateOfBirth = "Date of Birth:\n$dateOfBirth"
                                                                                                                                            if (txtDateOfBirth != null) {
                                                                                                                                                txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                            }

                                                                                                                                            val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                            val displaySiteBranch = "Office/Site Branch:\n$officeSiteBranch"
                                                                                                                                            if (txtSiteBranch != null) {
                                                                                                                                                txtSiteBranch.text = displaySiteBranch
                                                                                                                                            }

                                                                                                                                            val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                            val displayDepartment = "Department:\n$department"
                                                                                                                                            if (txtDepartment != null) {
                                                                                                                                                txtDepartment.text = displayDepartment
                                                                                                                                            }

                                                                                                                                            val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                            val displayJobTitle = "Job Title:\n$jobTitle"
                                                                                                                                            if (txtJobTitle != null) {
                                                                                                                                                txtJobTitle.text = displayJobTitle
                                                                                                                                            }

                                                                                                                                            val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                            val displayEmail = "E-Mail Address:\n$emailAddress"
                                                                                                                                            if (txtEmail != null) {
                                                                                                                                                txtEmail.text = displayEmail
                                                                                                                                            }

                                                                                                                                            val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                            val displayTelephone = "Telephone No.:\n$telephoneNumber"
                                                                                                                                            if (txtTelephone != null) {
                                                                                                                                                txtTelephone.text = displayTelephone
                                                                                                                                            }

                                                                                                                                            val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                            if (btnOkAttendanceConfirm != null) {
                                                                                                                                                btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                                    dialog6.dismiss()
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            dialog6.show()
                                                                                                                                            dialog6.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                                            dialog6.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                                            dialog6.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                                            dialog6.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        dialog5.show()
                                                                                                                        dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                        dialog5.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                        dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                        dialog5.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    dialog4.show()
                                                                                                    dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                    dialog4.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                    dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                    dialog4.window!!.setGravity(Gravity.BOTTOM)

                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            dialog3.show()
                                                                            dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                            dialog3.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                            dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                            dialog3.window!!.setGravity(Gravity.BOTTOM)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    dialog2.show()
                                                    dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                    dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                    dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                    dialog2.window!!.setGravity(Gravity.BOTTOM)

                                                } else if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()){
                                                    val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                                    val dialog2 = BottomSheetDialog(this)
                                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                    dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                                    val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                                    val adapter1 = ArrayAdapter(this, R.layout.dropdown_item, genders)

                                                    val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                                    if (spinnerGenderSignUp != null) {
                                                        spinnerGenderSignUp.setAdapter(adapter1)
                                                    }

                                                    val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                                    val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                                    if (editTxtDateOfBirthSignUp != null) {
                                                        editTxtDateOfBirthSignUp.setOnClickListener {
                                                            val c = Calendar.getInstance()

                                                            val year1 = c.get(Calendar. YEAR)
                                                            val month = c.get(Calendar. MONTH)
                                                            val day = c.get(Calendar. DAY_OF_MONTH)

                                                            val datePickerDialog = DatePickerDialog(
                                                                this,
                                                                { _, year, monthOfYear, dayOfMonth ->
                                                                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                                                                    editTxtDateOfBirthSignUp.setText(date)
                                                                },
                                                                year1,
                                                                month,
                                                                day
                                                            )
                                                            datePickerDialog.show()
                                                        }
                                                    }

                                                    val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                                    if (btnCancelPersonalDetails != null) {
                                                        btnCancelPersonalDetails.setOnClickListener{
                                                            dialog2.dismiss()
                                                        }
                                                    }

                                                    val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                                    if (btnNextSignUpJobDescription != null) {
                                                        btnNextSignUpJobDescription.setOnClickListener {
                                                            if (spinnerGenderSignUp != null) {
                                                                if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                                    Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                                } else if (editTxtNationalIDNo != null) {
                                                                    if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                        Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                                    } else if (editTxtDateOfBirthSignUp != null) {
                                                                        if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                            Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                        } else{
                                                                            val passGender = spinnerGenderSignUp.text.toString()
                                                                            val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                            val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                            val dialog3 = BottomSheetDialog(this)
                                                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                            dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                            val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                            val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                            val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                            val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                            if (btnCancelJobDescription != null) {
                                                                                btnCancelJobDescription.setOnClickListener{
                                                                                    dialog3.dismiss()
                                                                                }
                                                                            }

                                                                            val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                            if (btnNextSignUpContactInformation != null) {
                                                                                btnNextSignUpContactInformation.setOnClickListener {
                                                                                    if (editTxtOfficeBranchSignUp != null) {
                                                                                        if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(this,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else if (editTxtDepartmentSignUp != null) {
                                                                                            if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                                Toast.makeText(this,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                            } else if (editTxtJobTitleSignUp != null) {
                                                                                                if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                                    Toast.makeText(this,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                } else {
                                                                                                    val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                                    val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                                    val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                                    val dialog4 = BottomSheetDialog(this)
                                                                                                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                    dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                                    val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                                    val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                                    val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                                    if (btnCancelContactInformation != null) {
                                                                                                        btnCancelContactInformation.setOnClickListener{
                                                                                                            dialog4.dismiss()
                                                                                                        }
                                                                                                    }

                                                                                                    val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                                    if (btnNextSignUpSetPIN != null) {
                                                                                                        btnNextSignUpSetPIN.setOnClickListener {
                                                                                                            if (editTxtTelephoneNumberSignUp != null) {
                                                                                                                if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                                    Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                } else if (editTxtEmailAddressSignUp != null) {
                                                                                                                    if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                        Toast.makeText(this,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                    } else{
                                                                                                                        val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                        val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                        val dialog5 = BottomSheetDialog(this)
                                                                                                                        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                        dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                        val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                        val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                        val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                        if (btnCancelSetPin != null) {
                                                                                                                            btnCancelSetPin.setOnClickListener{
                                                                                                                                dialog5.dismiss()
                                                                                                                            }
                                                                                                                        }

                                                                                                                        val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                        if (btnFinishSignUp != null) {
                                                                                                                            btnFinishSignUp.setOnClickListener {
                                                                                                                                if (editTxtPINSignUp != null) {
                                                                                                                                    if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                        Toast.makeText(this,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                    } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                        if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                            Toast.makeText(this,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                        } else{
                                                                                                                                            val emailAddress = passEmailAddress
                                                                                                                                            val telephoneNumber = passTelephoneNumber
                                                                                                                                            val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                            val department = passDepartment
                                                                                                                                            val jobTitle = passJobTitle
                                                                                                                                            val gender = passGender
                                                                                                                                            val nationalId = passNationalId
                                                                                                                                            val dateOfBirth = passDateOfBirth
                                                                                                                                            val name = passName
                                                                                                                                            val accountTag = accountTag1
                                                                                                                                            val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                            val company = "to be set"
                                                                                                                                            val companyInitials = "to be set"
                                                                                                                                            val companyAdmissionKey = "to be set"

                                                                                                                                            val db = DBHelper(this, null)
                                                                                                                                            db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                            dialog.dismiss()
                                                                                                                                            dialog1.dismiss()
                                                                                                                                            dialog2.dismiss()
                                                                                                                                            dialog3.dismiss()
                                                                                                                                            dialog4.dismiss()
                                                                                                                                            dialog5.dismiss()

                                                                                                                                            val dialog6 = BottomSheetDialog(this)
                                                                                                                                            dialog6.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                                            dialog6.setContentView(R.layout.bottomsheet_confirmaccount)

                                                                                                                                            val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                            val displayName = "Name:\n$name"
                                                                                                                                            if (txtName != null) {
                                                                                                                                                txtName.text = displayName
                                                                                                                                            }

                                                                                                                                            val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                            val displayGender = "Gender:\n$gender"
                                                                                                                                            if (txtGender != null) {
                                                                                                                                                txtGender.text = displayGender
                                                                                                                                            }

                                                                                                                                            val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                            val displayNationalId = "National ID No.:\n$nationalId"
                                                                                                                                            if (txtNationalId != null) {
                                                                                                                                                txtNationalId.text = displayNationalId
                                                                                                                                            }

                                                                                                                                            val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                            val displayDateOfBirth = "Date of Birth:\n$dateOfBirth"
                                                                                                                                            if (txtDateOfBirth != null) {
                                                                                                                                                txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                            }

                                                                                                                                            val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                            val displaySiteBranch = "Office/Site Branch:\n$officeSiteBranch"
                                                                                                                                            if (txtSiteBranch != null) {
                                                                                                                                                txtSiteBranch.text = displaySiteBranch
                                                                                                                                            }

                                                                                                                                            val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                            val displayDepartment = "Department:\n$department"
                                                                                                                                            if (txtDepartment != null) {
                                                                                                                                                txtDepartment.text = displayDepartment
                                                                                                                                            }

                                                                                                                                            val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                            val displayJobTitle = "Job Title:\n$jobTitle"
                                                                                                                                            if (txtJobTitle != null) {
                                                                                                                                                txtJobTitle.text = displayJobTitle
                                                                                                                                            }

                                                                                                                                            val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                            val displayEmail = "E-Mail Address:\n$emailAddress"
                                                                                                                                            if (txtEmail != null) {
                                                                                                                                                txtEmail.text = displayEmail
                                                                                                                                            }

                                                                                                                                            val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                            val displayTelephone = "Telephone No.:\n$telephoneNumber"
                                                                                                                                            if (txtTelephone != null) {
                                                                                                                                                txtTelephone.text = displayTelephone
                                                                                                                                            }

                                                                                                                                            val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                            if (btnOkAttendanceConfirm != null) {
                                                                                                                                                btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                                    dialog6.dismiss()
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            dialog6.show()
                                                                                                                                            dialog6.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                                            dialog6.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                                            dialog6.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                                            dialog6.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        dialog5.show()
                                                                                                                        dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                        dialog5.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                        dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                        dialog5.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    dialog4.show()
                                                                                                    dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                    dialog4.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                    dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                    dialog4.window!!.setGravity(Gravity.BOTTOM)

                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            dialog3.show()
                                                                            dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                            dialog3.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                            dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                            dialog3.window!!.setGravity(Gravity.BOTTOM)


                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    dialog2.show()
                                                    dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                    dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                    dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                    dialog2.window!!.setGravity(Gravity.BOTTOM)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            dialog1.show()
                            dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog1.window!!.setGravity(Gravity.BOTTOM)

                        } else if (editTxtAdminKey.text.toString().isEmpty()){
                            Toast.makeText(this,"INSERT ADMIN KEY", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,"INSERT VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            val btnUserSignUp = dialog.findViewById<LinearLayout>(R.id.btnUserSignUp)
            if (btnUserSignUp != null) {
                btnUserSignUp.setOnClickListener {
                    displayAccountTag = "USER"

                    val dialog1 = BottomSheetDialog(this)
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog1.setContentView(R.layout.bottomsheet_signupname)

                    val txtAccountTypeSignUp = dialog1.findViewById<TextView>(R.id.txtAccountTypeSignUp)
                    if (txtAccountTypeSignUp != null) {
                        txtAccountTypeSignUp.text = displayAccountTag
                    }
                    val accountTag1 = txtAccountTypeSignUp?.text.toString()

                    val editTxtFirstNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtFirstNameSignUp)
                    val editTxtMiddleNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtMiddleNameSignUp)
                    val editTxtLastNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtLastNameSignUp)

                    val btnCancelName = dialog1.findViewById<ImageView>(R.id.btnCancelName)
                    if (btnCancelName != null) {
                        btnCancelName.setOnClickListener{
                            dialog1.dismiss()
                        }
                    }

                    val btnNextSignUpPersonalDetails = dialog1.findViewById<Button>(R.id.btnNextSignUpPersonalDetails)
                    if (btnNextSignUpPersonalDetails != null) {
                        btnNextSignUpPersonalDetails.setOnClickListener{
                            if (editTxtFirstNameSignUp != null) {
                                if(editTxtFirstNameSignUp.text.toString().trim().isEmpty()){
                                    Toast.makeText(this, "FIRST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                } else if (editTxtLastNameSignUp != null) {
                                    if(editTxtLastNameSignUp.text.toString().trim().isEmpty()){
                                        Toast.makeText(this, "LAST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else if (editTxtMiddleNameSignUp != null) {
                                        if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtMiddleNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()) {
                                            val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtMiddleNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                            val dialog2 = BottomSheetDialog(this)
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                            val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                            val adapter1 = ArrayAdapter(this, R.layout.dropdown_item, genders)

                                            val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                            if (spinnerGenderSignUp != null) {
                                                spinnerGenderSignUp.setAdapter(adapter1)
                                            }

                                            val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                            val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                            if (editTxtDateOfBirthSignUp != null) {
                                                editTxtDateOfBirthSignUp.setOnClickListener {
                                                    val c = Calendar.getInstance()

                                                    val year1 = c.get(Calendar. YEAR)
                                                    val month = c.get(Calendar. MONTH)
                                                    val day = c.get(Calendar. DAY_OF_MONTH)

                                                    val datePickerDialog = DatePickerDialog(
                                                        this,
                                                        { _, year, monthOfYear, dayOfMonth ->
                                                            val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                                                            editTxtDateOfBirthSignUp.setText(date)
                                                        },
                                                        year1,
                                                        month,
                                                        day
                                                    )
                                                    datePickerDialog.show()
                                                }
                                            }

                                            val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                            if (btnCancelPersonalDetails != null) {
                                                btnCancelPersonalDetails.setOnClickListener{
                                                    dialog2.dismiss()
                                                }
                                            }

                                            val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                            if (btnNextSignUpJobDescription != null) {
                                                btnNextSignUpJobDescription.setOnClickListener {
                                                    if (spinnerGenderSignUp != null) {
                                                        if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                            Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                        } else if (editTxtNationalIDNo != null) {
                                                            if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                            } else if (editTxtDateOfBirthSignUp != null) {
                                                                if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                    Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                } else{
                                                                    val passGender = spinnerGenderSignUp.text.toString()
                                                                    val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                    val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                    val dialog3 = BottomSheetDialog(this)
                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                    dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                    val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                    val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                    val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                    val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                    if (btnCancelJobDescription != null) {
                                                                        btnCancelJobDescription.setOnClickListener{
                                                                            dialog3.dismiss()
                                                                        }
                                                                    }

                                                                    val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                    if (btnNextSignUpContactInformation != null) {
                                                                        btnNextSignUpContactInformation.setOnClickListener {
                                                                            if (editTxtOfficeBranchSignUp != null) {
                                                                                if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                    Toast.makeText(this,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                } else if (editTxtDepartmentSignUp != null) {
                                                                                    if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                        Toast.makeText(this,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                    } else if (editTxtJobTitleSignUp != null) {
                                                                                        if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(this,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else {
                                                                                            val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                            val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                            val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                            val dialog4 = BottomSheetDialog(this)
                                                                                            dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                            dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                            val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                            val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                            val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                            if (btnCancelContactInformation != null) {
                                                                                                btnCancelContactInformation.setOnClickListener{
                                                                                                    dialog4.dismiss()
                                                                                                }
                                                                                            }

                                                                                            val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                            if (btnNextSignUpSetPIN != null) {
                                                                                                btnNextSignUpSetPIN.setOnClickListener {
                                                                                                    if (editTxtTelephoneNumberSignUp != null) {
                                                                                                        if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                            Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                        } else if (editTxtEmailAddressSignUp != null) {
                                                                                                            if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                Toast.makeText(this,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                            } else{
                                                                                                                val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                val dialog5 = BottomSheetDialog(this)
                                                                                                                dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                if (btnCancelSetPin != null) {
                                                                                                                    btnCancelSetPin.setOnClickListener{
                                                                                                                        dialog5.dismiss()
                                                                                                                    }
                                                                                                                }

                                                                                                                val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                if (btnFinishSignUp != null) {
                                                                                                                    btnFinishSignUp.setOnClickListener {
                                                                                                                        if (editTxtPINSignUp != null) {
                                                                                                                            if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                Toast.makeText(this,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                            } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                    Toast.makeText(this,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                } else{
                                                                                                                                    val emailAddress = passEmailAddress
                                                                                                                                    val telephoneNumber = passTelephoneNumber
                                                                                                                                    val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                    val department = passDepartment
                                                                                                                                    val jobTitle = passJobTitle
                                                                                                                                    val gender = passGender
                                                                                                                                    val nationalId = passNationalId
                                                                                                                                    val dateOfBirth = passDateOfBirth
                                                                                                                                    val name = passName
                                                                                                                                    val accountTag = accountTag1
                                                                                                                                    val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                    val company = "to be set"
                                                                                                                                    val companyInitials = "to be set"
                                                                                                                                    val companyAdmissionKey = "to be set"

                                                                                                                                    val db = DBHelper(this, null)
                                                                                                                                    db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                    dialog.dismiss()
                                                                                                                                    dialog1.dismiss()
                                                                                                                                    dialog2.dismiss()
                                                                                                                                    dialog3.dismiss()
                                                                                                                                    dialog4.dismiss()
                                                                                                                                    dialog5.dismiss()

                                                                                                                                    val dialog6 = BottomSheetDialog(this)
                                                                                                                                    dialog6.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                                    dialog6.setContentView(R.layout.bottomsheet_confirmaccount)

                                                                                                                                    val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                    val displayName = "Name:\n$name"
                                                                                                                                    if (txtName != null) {
                                                                                                                                        txtName.text = displayName
                                                                                                                                    }

                                                                                                                                    val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                    val displayGender = "Gender:\n$gender"
                                                                                                                                    if (txtGender != null) {
                                                                                                                                        txtGender.text = displayGender
                                                                                                                                    }

                                                                                                                                    val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                    val displayNationalId = "National ID No.:\n$nationalId"
                                                                                                                                    if (txtNationalId != null) {
                                                                                                                                        txtNationalId.text = displayNationalId
                                                                                                                                    }

                                                                                                                                    val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                    val displayDateOfBirth = "Date of Birth:\n$dateOfBirth"
                                                                                                                                    if (txtDateOfBirth != null) {
                                                                                                                                        txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                    }

                                                                                                                                    val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                    val displaySiteBranch = "Office/Site Branch:\n$officeSiteBranch"
                                                                                                                                    if (txtSiteBranch != null) {
                                                                                                                                        txtSiteBranch.text = displaySiteBranch
                                                                                                                                    }

                                                                                                                                    val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                    val displayDepartment = "Department:\n$department"
                                                                                                                                    if (txtDepartment != null) {
                                                                                                                                        txtDepartment.text = displayDepartment
                                                                                                                                    }

                                                                                                                                    val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                    val displayJobTitle = "Job Title:\n$jobTitle"
                                                                                                                                    if (txtJobTitle != null) {
                                                                                                                                        txtJobTitle.text = displayJobTitle
                                                                                                                                    }

                                                                                                                                    val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                    val displayEmail = "E-Mail Address:\n$emailAddress"
                                                                                                                                    if (txtEmail != null) {
                                                                                                                                        txtEmail.text = displayEmail
                                                                                                                                    }

                                                                                                                                    val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                    val displayTelephone = "Telephone No.:\n$telephoneNumber"
                                                                                                                                    if (txtTelephone != null) {
                                                                                                                                        txtTelephone.text = displayTelephone
                                                                                                                                    }

                                                                                                                                    val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                    if (btnOkAttendanceConfirm != null) {
                                                                                                                                        btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                            dialog6.dismiss()
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    dialog6.show()
                                                                                                                                    dialog6.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                                    dialog6.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                                    dialog6.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                                    dialog6.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                                dialog5.show()
                                                                                                                dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                dialog5.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                dialog5.window!!.setGravity(Gravity.BOTTOM)

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            dialog4.show()
                                                                                            dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                            dialog4.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                            dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                            dialog4.window!!.setGravity(Gravity.BOTTOM)

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    dialog3.show()
                                                                    dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                    dialog3.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                    dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                    dialog3.window!!.setGravity(Gravity.BOTTOM)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            dialog2.show()
                                            dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                            dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                            dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                            dialog2.window!!.setGravity(Gravity.BOTTOM)

                                        } else if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()){
                                            val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                            val dialog2 = BottomSheetDialog(this)
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                            val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                            val adapter1 = ArrayAdapter(this, R.layout.dropdown_item, genders)

                                            val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                            if (spinnerGenderSignUp != null) {
                                                spinnerGenderSignUp.setAdapter(adapter1)
                                            }

                                            val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                            val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                            if (editTxtDateOfBirthSignUp != null) {
                                                editTxtDateOfBirthSignUp.setOnClickListener {
                                                    val c = Calendar.getInstance()

                                                    val year1 = c.get(Calendar. YEAR)
                                                    val month = c.get(Calendar. MONTH)
                                                    val day = c.get(Calendar. DAY_OF_MONTH)

                                                    val datePickerDialog = DatePickerDialog(
                                                        this,
                                                        { _, year, monthOfYear, dayOfMonth ->
                                                            val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                                                            editTxtDateOfBirthSignUp.setText(date)
                                                        },
                                                        year1,
                                                        month,
                                                        day
                                                    )
                                                    datePickerDialog.show()
                                                }
                                            }

                                            val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                            if (btnCancelPersonalDetails != null) {
                                                btnCancelPersonalDetails.setOnClickListener{
                                                    dialog2.dismiss()
                                                }
                                            }

                                            val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                            if (btnNextSignUpJobDescription != null) {
                                                btnNextSignUpJobDescription.setOnClickListener {
                                                    if (spinnerGenderSignUp != null) {
                                                        if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                            Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                        } else if (editTxtNationalIDNo != null) {
                                                            if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                            } else if (editTxtDateOfBirthSignUp != null) {
                                                                if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                    Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                } else{
                                                                    val passGender = spinnerGenderSignUp.text.toString()
                                                                    val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                    val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                    val dialog3 = BottomSheetDialog(this)
                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                    dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                    val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                    val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                    val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                    val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                    if (btnCancelJobDescription != null) {
                                                                        btnCancelJobDescription.setOnClickListener{
                                                                            dialog3.dismiss()
                                                                        }
                                                                    }

                                                                    val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                    if (btnNextSignUpContactInformation != null) {
                                                                        btnNextSignUpContactInformation.setOnClickListener {
                                                                            if (editTxtOfficeBranchSignUp != null) {
                                                                                if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                    Toast.makeText(this,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                } else if (editTxtDepartmentSignUp != null) {
                                                                                    if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                        Toast.makeText(this,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                    } else if (editTxtJobTitleSignUp != null) {
                                                                                        if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(this,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else {
                                                                                            val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                            val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                            val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                            val dialog4 = BottomSheetDialog(this)
                                                                                            dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                            dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                            val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                            val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                            val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                            if (btnCancelContactInformation != null) {
                                                                                                btnCancelContactInformation.setOnClickListener{
                                                                                                    dialog4.dismiss()
                                                                                                }
                                                                                            }

                                                                                            val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                            if (btnNextSignUpSetPIN != null) {
                                                                                                btnNextSignUpSetPIN.setOnClickListener {
                                                                                                    if (editTxtTelephoneNumberSignUp != null) {
                                                                                                        if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                            Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                        } else if (editTxtEmailAddressSignUp != null) {
                                                                                                            if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                Toast.makeText(this,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                            } else{
                                                                                                                val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                val dialog5 = BottomSheetDialog(this)
                                                                                                                dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                if (btnCancelSetPin != null) {
                                                                                                                    btnCancelSetPin.setOnClickListener{
                                                                                                                        dialog5.dismiss()
                                                                                                                    }
                                                                                                                }

                                                                                                                val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                if (btnFinishSignUp != null) {
                                                                                                                    btnFinishSignUp.setOnClickListener {
                                                                                                                        if (editTxtPINSignUp != null) {
                                                                                                                            if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                Toast.makeText(this,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                            } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                    Toast.makeText(this,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                } else{
                                                                                                                                    val emailAddress = passEmailAddress
                                                                                                                                    val telephoneNumber = passTelephoneNumber
                                                                                                                                    val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                    val department = passDepartment
                                                                                                                                    val jobTitle = passJobTitle
                                                                                                                                    val gender = passGender
                                                                                                                                    val nationalId = passNationalId
                                                                                                                                    val dateOfBirth = passDateOfBirth
                                                                                                                                    val name = passName
                                                                                                                                    val accountTag = accountTag1
                                                                                                                                    val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                    val company = "to be set"
                                                                                                                                    val companyInitials = "to be set"
                                                                                                                                    val companyAdmissionKey = "to be set"

                                                                                                                                    val db = DBHelper(this, null)
                                                                                                                                    db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                    dialog.dismiss()
                                                                                                                                    dialog1.dismiss()
                                                                                                                                    dialog2.dismiss()
                                                                                                                                    dialog3.dismiss()
                                                                                                                                    dialog4.dismiss()
                                                                                                                                    dialog5.dismiss()

                                                                                                                                    val dialog6 = BottomSheetDialog(this)
                                                                                                                                    dialog6.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                                    dialog6.setContentView(R.layout.bottomsheet_confirmaccount)

                                                                                                                                    val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                    val displayName = "Name:\n$name"
                                                                                                                                    if (txtName != null) {
                                                                                                                                        txtName.text = displayName
                                                                                                                                    }

                                                                                                                                    val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                    val displayGender = "Gender:\n$gender"
                                                                                                                                    if (txtGender != null) {
                                                                                                                                        txtGender.text = displayGender
                                                                                                                                    }

                                                                                                                                    val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                    val displayNationalId = "National ID No.:\n$nationalId"
                                                                                                                                    if (txtNationalId != null) {
                                                                                                                                        txtNationalId.text = displayNationalId
                                                                                                                                    }

                                                                                                                                    val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                    val displayDateOfBirth = "Date of Birth:\n$dateOfBirth"
                                                                                                                                    if (txtDateOfBirth != null) {
                                                                                                                                        txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                    }

                                                                                                                                    val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                    val displaySiteBranch = "Office/Site Branch:\n$officeSiteBranch"
                                                                                                                                    if (txtSiteBranch != null) {
                                                                                                                                        txtSiteBranch.text = displaySiteBranch
                                                                                                                                    }

                                                                                                                                    val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                    val displayDepartment = "Department:\n$department"
                                                                                                                                    if (txtDepartment != null) {
                                                                                                                                        txtDepartment.text = displayDepartment
                                                                                                                                    }

                                                                                                                                    val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                    val displayJobTitle = "Job Title:\n$jobTitle"
                                                                                                                                    if (txtJobTitle != null) {
                                                                                                                                        txtJobTitle.text = displayJobTitle
                                                                                                                                    }

                                                                                                                                    val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                    val displayEmail = "E-Mail Address:\n$emailAddress"
                                                                                                                                    if (txtEmail != null) {
                                                                                                                                        txtEmail.text = displayEmail
                                                                                                                                    }

                                                                                                                                    val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                    val displayTelephone = "Telephone No.:\n$telephoneNumber"
                                                                                                                                    if (txtTelephone != null) {
                                                                                                                                        txtTelephone.text = displayTelephone
                                                                                                                                    }

                                                                                                                                    val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                    if (btnOkAttendanceConfirm != null) {
                                                                                                                                        btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                            dialog6.dismiss()
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    dialog6.show()
                                                                                                                                    dialog6.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                                    dialog6.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                                    dialog6.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                                    dialog6.window!!.setGravity(Gravity.BOTTOM)

                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                                dialog5.show()
                                                                                                                dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                dialog5.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                                                dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                dialog5.window!!.setGravity(Gravity.BOTTOM)

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            dialog4.show()
                                                                                            dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                            dialog4.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                                            dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                            dialog4.window!!.setGravity(Gravity.BOTTOM)

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    dialog3.show()
                                                                    dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                    dialog3.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                                    dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                    dialog3.window!!.setGravity(Gravity.BOTTOM)


                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            dialog2.show()
                                            dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                            dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                            dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                            dialog2.window!!.setGravity(Gravity.BOTTOM)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    dialog1.show()
                    dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                    dialog1.window!!.setGravity(Gravity.BOTTOM)
                }
            }
            dialog.show()
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
    }
    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return true
    }
    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        finishAffinity()
    }

}