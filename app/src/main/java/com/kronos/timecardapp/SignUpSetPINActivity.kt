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

        val editTxtPINSignUp = findViewById<EditText>(R.id.editTxtPINSignUp)
        val editTxtConfirmPINSignUp = findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

        val btnFinishSignUp = findViewById<Button>(R.id.btnFinishSignUp)
        btnFinishSignUp.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            if(editTxtPINSignUp.toString().trim() != editTxtConfirmPINSignUp.toString().trim()){
                Toast.makeText(this,"Enter Matching PINs",Toast.LENGTH_SHORT).show()
            } else{
                startActivity(intent)
            }
        }
        val btnBackToContactInformation = findViewById<Button>(R.id.btnBackToContactInformation)
        btnBackToContactInformation.setOnClickListener {
            val intent = Intent(this,SignUpContactInformationActivity::class.java)
            startActivity(intent)
        }
    }
}