package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpContactInformationActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupcontactinformation)

        val bundle : Bundle? = intent.extras

        val officeSiteBranch1 = bundle!!.getString("officeSiteBranchKey")
        val department1 = bundle.getString("departmentKey")
        val jobTitle1 = bundle.getString("jobTitleKey")
        val gender2 = bundle.getString("genderKey1")
        val nationalId2 = bundle.getString("nationalIdKey1")
        val dateOfBirth2 = bundle.getString("dateOfBirthKey1")
        val name3 = bundle.getString("nameKey2")
        val accountTag4 = bundle.getString("accountTagKey3")

        val editTxtTelephoneNumberSignUp = findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
        val editTxtEmailAddressSignUp = findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

        val btnNextSignUpSetPIN = findViewById<Button>(R.id.btnNextSignUpSetPIN)
        btnNextSignUpSetPIN.setOnClickListener {

            val intent = Intent(this, SignUpSetPINActivity::class.java)
            if (editTxtTelephoneNumberSignUp.text.toString().trim() == ""){
                Toast.makeText(this,"Telephone Number is required", Toast.LENGTH_SHORT).show()
            } else{
                val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                intent.putExtra("accountTagKey4",accountTag4)
                intent.putExtra("nameKey3",name3)
                intent.putExtra("genderKey2",gender2)
                intent.putExtra("nationalIdKey2",nationalId2)
                intent.putExtra("dateOfBirthKey2",dateOfBirth2)
                intent.putExtra("officeSiteBranchKey1",officeSiteBranch1)
                intent.putExtra("departmentKey1",department1)
                intent.putExtra("jobTitleKey1",jobTitle1)
                intent.putExtra("emailAddressKey",passEmailAddress)
                intent.putExtra("telephoneNumberKey",passTelephoneNumber)

                startActivity(intent)
            }
        }
        val btnBackToJobDescription = findViewById<Button>(R.id.btnBackToJobDescription)
        btnBackToJobDescription.setOnClickListener {
            val intent = Intent(this, SignUpJobDescriptionActivity::class.java)
            startActivity(intent)
        }
    }
}

