package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction

class LoginActivity : AppCompatActivity(){
    private lateinit var editTxtFullNameNationalIdLogIn: EditText
    private lateinit var editTxtPINLogIn: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTxtFullNameNationalIdLogIn = findViewById(R.id.editTxtFullNameNationalIdLogIn)
        editTxtPINLogIn = findViewById(R.id.editTxtPINLogIn)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {

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
                        //val checkNationalId = nationalIdLogIn.toString()

                        val intent = Intent(this,CreateCompanyActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
                        //intent.putExtra("checkNationalIdKey",checkNationalId)
                        editTxtFullNameNationalIdLogIn.text.clear()
                        editTxtPINLogIn.text.clear()
                        startActivity(intent)

                    } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "ADMINISTRATOR"){
                        val displayAccountName = nameLogIn.toString()
                        val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                        val displayDepartment = departmentLogIn.toString()
                        val displayJobTitle = jobTitleLogIn.toString()
                        //val checkNationalId = nationalIdLogIn.toString()

                        val intent = Intent(this,CreateCompanyActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
                        //intent.putExtra("checkNationalIdKey",checkNationalId)
                        editTxtFullNameNationalIdLogIn.text.clear()
                        editTxtPINLogIn.text.clear()
                        startActivity(intent)

                    } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                        val displayAccountName = nameLogIn.toString()
                        val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                        val displayDepartment = departmentLogIn.toString()
                        val displayJobTitle = jobTitleLogIn.toString()
                        //val checkNationalId = nationalIdLogIn.toString()

                        val intent = Intent(this,JoinCompanyActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
                        //intent.putExtra("checkNationalIdKey",checkNationalId)
                        editTxtFullNameNationalIdLogIn.text.clear()
                        editTxtPINLogIn.text.clear()
                        startActivity(intent)

                    } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() == "to be set" && accountTagLogIn.toString() == "USER"){
                        val displayAccountName = nameLogIn.toString()
                        val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                        val displayDepartment = departmentLogIn.toString()
                        val displayJobTitle = jobTitleLogIn.toString()
                        //val checkNationalId = nationalIdLogIn.toString()

                        val intent = Intent(this,JoinCompanyActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
                        //intent.putExtra("checkNationalIdKey",checkNationalId)
                        editTxtFullNameNationalIdLogIn.text.clear()
                        editTxtPINLogIn.text.clear()

                        startActivity(intent)

                    } else if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString() && companyNameLogIn.toString() != "to be set" && (accountTagLogIn.toString() == "ADMINISTRATOR" || accountTagLogIn.toString() == "USER")){
                        val displayAccountName = nameLogIn.toString()
                        val displayOfficeSiteBranch = officeSiteBranchLogIn.toString()
                        val displayDepartment = departmentLogIn.toString()
                        val displayJobTitle = jobTitleLogIn.toString()
                        val displayCompanyName = companyNameLogIn.toString()

                        val intent = Intent(this,HomePageDrawerActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
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
                        val displayCompanyName = companyNameLogIn.toString()

                        val intent = Intent(this,HomePageDrawerActivity::class.java)
                        intent.putExtra("nameLogInKey1",displayAccountName)
                        intent.putExtra("displayOfficeSiteBranchKey1",displayOfficeSiteBranch)
                        intent.putExtra("displayDepartmentKey1",displayDepartment)
                        intent.putExtra("displayJobTitleKey1",displayJobTitle)
                        intent.putExtra("displayCompanyNameKey1",displayCompanyName)

                        editTxtFullNameNationalIdLogIn.text.clear()
                        editTxtPINLogIn.text.clear()
                        startActivity(intent)

                        Toast.makeText(this,"LOGIN SUCCESSFUL;\nWELCOME $displayAccountName",Toast.LENGTH_SHORT).show()
                    }
                } while(cursor.moveToNext())
            }
            cursor.close()
        }

        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}