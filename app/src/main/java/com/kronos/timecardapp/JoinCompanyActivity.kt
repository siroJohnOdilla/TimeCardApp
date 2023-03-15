package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JoinCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joincompany)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle : Bundle? = intent.extras

        val passLogInName = bundle!!.getString("nameLogInKey1")
        val passLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey1")
        val passLogInDepartment = bundle.getString("displayDepartmentKey1")
        val passJobTitle = bundle.getString("displayJobTitleKey1")
        val passAccountTag = bundle.getString("accountTagCheck1")

        val editTxtJoinCompanyAdmissionKey = findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKey)

        val btnSubmitAdmissionKey = findViewById<Button>(R.id.btnSubmitAdmissionKey)
        btnSubmitAdmissionKey.setOnClickListener {
            val intent = Intent(this,HomePageDrawerActivity::class.java)
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

                                    if(passLogInName.toString() == nameLogin.toString()){
                                        val id = idLogin.toLong()

                                        db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKey)
                                        db.close()

                                        intent.putExtra("nameLogInKey1", passLogInName)
                                        intent.putExtra("displayOfficeSiteBranchKey1", passLoginOfficeSiteBranch)
                                        intent.putExtra("displayDepartmentKey1", passLogInDepartment)
                                        intent.putExtra("displayJobTitleKey1", passJobTitle)
                                        intent.putExtra("accountTagCheck1",passAccountTag)
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}