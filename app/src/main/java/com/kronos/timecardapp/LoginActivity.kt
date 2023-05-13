package com.kronos.timecardapp

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
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
                                txtAttendanceConfirm.text = displayAccountName
                            }
                            val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                            if (txtTimeAttendanceConfirm != null) {
                                val displayTime = "TIME IN:\n$timeIn"
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
                                txtAttendanceConfirm.text = displayAccountName
                            }
                            val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                            if (txtTimeAttendanceConfirm != null) {
                                val displayTime = "TIME IN:\n$timeIn"
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
                                            txtAttendanceConfirm.text = name1
                                        }
                                        val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                        if (txtTimeAttendanceConfirm != null) {
                                            val display = "TIME OUT: $timeOut1\n$totalTimeWorked1"
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
                                            txtAttendanceConfirm.text = name1
                                        }
                                        val txtTimeAttendanceConfirm = dialog.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                        if (txtTimeAttendanceConfirm != null) {
                                            val display = "TIME OUT: $timeOut1\n$totalTimeWorked1"
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

                            val intent = Intent(this,CreateCompanyActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val intent = Intent(this,CreateCompanyActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val intent = Intent(this,JoinCompanyActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

                        } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                            val displayAccountName = nameLogIn.toString()
                            val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                            val displayDepartment = departmentLogIn.toString()
                            val displayJobTitle = jobTitleLogIn.toString()
                            val accountTagCheck = accountTagLogIn.toString()

                            val intent = Intent(this,JoinCompanyActivity::class.java)
                            intent.putExtra("nameLogInKey1",displayAccountName)
                            intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey1",displayDepartment)
                            intent.putExtra("displayJobTitleKey1",displayJobTitle)
                            intent.putExtra("accountTagCheck1",accountTagCheck)
                            editTxtFullNameNationalIdLogIn.text.clear()
                            editTxtPINLogIn.text.clear()

                            startActivity(intent)

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

                        } else if (editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
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

                            Toast.makeText(this,"LOGIN SUCCESSFUL;\nWELCOME $displayAccountName",Toast.LENGTH_SHORT).show()
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
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
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