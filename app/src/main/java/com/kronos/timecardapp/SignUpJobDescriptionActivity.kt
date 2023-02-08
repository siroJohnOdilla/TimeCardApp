package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpJobDescriptionActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupjobdescription)

        val bundle : Bundle? = intent.extras

        val gender1 = bundle!!.getString("genderKey")
        val nationalId1 = bundle.getString("nationalIdKey")
        val dateOfBirth1 = bundle.getString("dateOfBirthKey")
        val name2 = bundle.getString("nameKey1")
        val accountTag3 = bundle.getString("accountTagKey2")


        val editTxtOfficeBranchSignUp = findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
        val editTxtDepartmentSignUp = findViewById<EditText>(R.id.editTxtDepartmentSignUp)
        val editTxtJobTitleSignUp = findViewById<EditText>(R.id.editTxtJobTitleSignUp)

        val btnNextSignUpContactInformation = findViewById<Button>(R.id.btnNextSignUpContactInformation)
        btnNextSignUpContactInformation.setOnClickListener {
            val intent = Intent(this,SignUpContactInformationActivity::class.java)

            if (editTxtOfficeBranchSignUp.text.toString().trim().uppercase() == ""){
                Toast.makeText(this,"Office/ Site Branch is required",Toast.LENGTH_SHORT).show()
            } else if (editTxtDepartmentSignUp.text.toString().trim().uppercase() == ""){
                Toast.makeText(this,"Department is Required",Toast.LENGTH_SHORT).show()
            } else if (editTxtJobTitleSignUp.text.toString().trim().uppercase() == ""){
                Toast.makeText(this,"Job Title is Required",Toast.LENGTH_SHORT).show()
            } else {
                val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                intent.putExtra("accountTagKey3",accountTag3)
                intent.putExtra("nameKey2",name2)
                intent.putExtra("genderKey1",gender1)
                intent.putExtra("nationalIdKey1",nationalId1)
                intent.putExtra("dateOfBirthKey1",dateOfBirth1)
                intent.putExtra("officeSiteBranchKey",passOfficeSiteBranch)
                intent.putExtra("departmentKey",passDepartment)
                intent.putExtra("jobTitleKey",passJobTitle)

                editTxtOfficeBranchSignUp.text.clear()
                editTxtDepartmentSignUp.text.clear()
                editTxtJobTitleSignUp.text.clear()

                startActivity(intent)
            }
        }
        val btnBackToPersonalDetails = findViewById<Button>(R.id.btnBackToPersonalDetails)
        btnBackToPersonalDetails.setOnClickListener {
            val intent = Intent(this,SignUpPersonalDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}