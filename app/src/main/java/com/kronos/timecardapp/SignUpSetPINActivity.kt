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

        val editTxtPINSignUp = findViewById<EditText>(R.id.editTxtPINSignUp)
        val editTxtConfirmPINSignUp = findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

        val btnFinishSignUp = findViewById<Button>(R.id.btnFinishSignUp)
        btnFinishSignUp.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()){
                Toast.makeText(this,"Enter Matching PINs",Toast.LENGTH_SHORT).show()
            } else{
                val db = DBHelper(this, null)

                val name = bundle!!.getString("name").toString().uppercase()
                val accountTag = bundle.getString("accountTag").toString()
                val gender = bundle.getString("gender").toString()
                val nationalId = bundle.getString("nationalId").toString()
                val dateOfBirth = bundle.getString("dateOfBirth").toString()
                val officeSiteBranch = bundle.getString("officeSiteBranch").toString()
                val department = bundle.getString("department").toString()
                val jobTitle = bundle.getString("jobTitle").toString()
                val emailAddress = bundle.getString("emailAddress").toString()
                val telephoneNumber = bundle.getString("telephoneNumber").toString()
                val pinNumber = editTxtConfirmPINSignUp.text.toString().trim()

                db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber)

                startActivity(intent)

                editTxtPINSignUp.text.clear()
                editTxtConfirmPINSignUp.text.clear()

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