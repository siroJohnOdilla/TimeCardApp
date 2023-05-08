package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val dayDisplay = SimpleDateFormat(" EEEE ", Locale.getDefault())
        val getCurrentDay = dayDisplay.format(Date())
        txtDisplayCurrentDay.text = getCurrentDay

        txtDisplayCurrentDate = findViewById(R.id.txtDisplayCurrentDate)
        val dateDisplay = SimpleDateFormat(" MMMM d, yyyy ", Locale.getDefault())
        val getCurrentDate = dateDisplay.format(Date())
        txtDisplayCurrentDate.text = getCurrentDate

        btnClockIn = findViewById(R.id.btnClockIn)
        btnClockIn.setOnClickListener {
            Toast.makeText(this,"CLOCK IN",Toast.LENGTH_SHORT).show()
        }
        btnClockOut = findViewById(R.id.btnClockOut)
        btnClockOut.setOnClickListener {
            Toast.makeText(this,"CLOCK OUT",Toast.LENGTH_SHORT).show()
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
                            Toast.makeText(this,"CREATE COMPANY",Toast.LENGTH_SHORT).show()

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
                            Toast.makeText(this,"CREATE COMPANY",Toast.LENGTH_SHORT).show()

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
                            Toast.makeText(this,"JOIN COMPANY",Toast.LENGTH_SHORT).show()

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
                            Toast.makeText(this,"JOIN COMPANY",Toast.LENGTH_SHORT).show()

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

                            Toast.makeText(this,"LOGIN SUCCESSFUL;\nWELCOME $displayAccountName",Toast.LENGTH_SHORT).show()

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
            Toast.makeText(this,"CREATE ACCOUNT",Toast.LENGTH_SHORT).show()
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