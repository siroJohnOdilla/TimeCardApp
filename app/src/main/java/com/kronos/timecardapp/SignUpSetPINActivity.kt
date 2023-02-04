package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpSetPINActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupsetpin)

        val bundle: Bundle? = intent.extras

        val emailAddress = bundle!!.getString("emailAddressKey").toString()
        val telephoneNumber = bundle.getString("telephoneNumberKey").toString()
        val officeSiteBranch = bundle.getString("officeSiteBranchKey1").toString()
        val department = bundle.getString("departmentKey1").toString()
        val jobTitle = bundle.getString("jobTitleKey1").toString()
        val gender = bundle.getString("genderKey2").toString()
        val nationalId = bundle.getString("nationalIdKey2").toString()
        val dateOfBirth = bundle.getString("dateOfBirthKey2").toString()
        val name = bundle.getString("nameKey3").toString()
        val accountTag = bundle.getString("accountTagKey4").toString()




        val editTxtPINSignUp = findViewById<EditText>(R.id.editTxtPINSignUp)
        val editTxtConfirmPINSignUp = findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

        val btnFinishSignUp = findViewById<Button>(R.id.btnFinishSignUp)
        btnFinishSignUp.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()){
                Toast.makeText(this,"Enter Matching PINs",Toast.LENGTH_SHORT).show()
            } else{
                val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()

                val db = DBHelper(this, null)

                db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber)

                startActivity(intent)
                /*editTxtPINSignUp.text.clear()
                editTxtConfirmPINSignUp.text.clear()*/
                Toast.makeText(this,"ACCOUNT SUCCESSFULLY CREATED",Toast.LENGTH_SHORT).show()
            }
        }
        val btnBackToContactInformation = findViewById<Button>(R.id.btnBackToContactInformation)
        btnBackToContactInformation.setOnClickListener {
            val intent = Intent(this,SignUpContactInformationActivity::class.java)
            startActivity(intent)
        }
    }
}