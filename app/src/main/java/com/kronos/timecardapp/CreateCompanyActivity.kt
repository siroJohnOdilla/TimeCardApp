package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createcompany)

        val bundle : Bundle? = intent.extras

        val passLogInName = bundle!!.getString("nameLogInKey1")
        val passLoginOfficeSiteBranch = bundle.getString("displayOfficeSiteBranchKey1")
        val passLogInDepartment = bundle.getString("displayDepartmentKey1")
        val passJobTitle = bundle.getString("displayJobTitleKey1")
        //val checkAltNationalId = bundle.getString("checkNationalId")

        val editTxtCompanyNameCreate = findViewById<EditText>(R.id.editTxtCompanyNameCreate)
        val editTxtCompanyNameInitials = findViewById<EditText>(R.id.editTxtCompanyNameInitials)
        val editTxtCompanyAdmissionKey = findViewById<EditText>(R.id.editTxtCompanyAdmissionKey)

        val btnFinishCompanyNameRegistration = findViewById<Button>(R.id.btnFinishCompanyNameRegistration)
        btnFinishCompanyNameRegistration.setOnClickListener {
            val intent = Intent(this,HomePageActivity::class.java)
            if(editTxtCompanyNameCreate.text.toString().trim().uppercase() == ""){
                Toast.makeText(this,"COMPANY NAME REQUIRED",Toast.LENGTH_SHORT).show()
            } else if (editTxtCompanyNameInitials.text.toString().trim().uppercase() == ""){
                Toast.makeText(this,"COMPANY INITIALS REQUIRED",Toast.LENGTH_SHORT).show()
            } else if (editTxtCompanyAdmissionKey.text.toString().trim().uppercase() == ""){
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

                        if (passLogInName.toString() == nameLogIn.toString() /*&& checkAltNationalId.toString() == nationalIdLogIn.toString()*/) {
                            val id = idLogIn.toLong()

                            db.updateCompany(id, company, companyInitials, companyAdmissionKey)
                            db.close()

                            intent.putExtra("nameLogInKey", passLogInName)
                            intent.putExtra("displayOfficeSiteBranchKey", passLoginOfficeSiteBranch)
                            intent.putExtra("displayDepartmentKey", passLogInDepartment)
                            intent.putExtra("displayJobTitleKey", passJobTitle)
                            intent.putExtra("displayCompanyNameKey", company)
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
        val btnBackToLoginPage2 = findViewById<Button>(R.id.btnBackToLoginPage2)
        btnBackToLoginPage2.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}