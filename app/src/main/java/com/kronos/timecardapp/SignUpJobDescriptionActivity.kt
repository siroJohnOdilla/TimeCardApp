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

        val editTxtOfficeBranchSignUp = findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
        val editTxtDepartmentSignUp = findViewById<EditText>(R.id.editTxtDepartmentSignUp)
        val editTxtJobTitleSignUp = findViewById<EditText>(R.id.editTxtJobTitleSignUp)

        val btnNextSignUpContactInformation = findViewById<Button>(R.id.btnNextSignUpContactInformation)
        btnNextSignUpContactInformation.setOnClickListener {
            intent = Intent(this,SignUpContactInformationActivity::class.java)
            if (editTxtOfficeBranchSignUp.text.toString().trim() == ""){
                Toast.makeText(this,"Office/ Site Branch is required",Toast.LENGTH_SHORT).show()
            } else if (editTxtDepartmentSignUp.text.toString().trim() == ""){
                Toast.makeText(this,"Department is Required",Toast.LENGTH_SHORT).show()
            } else if (editTxtJobTitleSignUp.text.toString().trim() == ""){
                Toast.makeText(this,"Job Title is Required",Toast.LENGTH_SHORT).show()
            } else {
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