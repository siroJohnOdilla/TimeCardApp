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

        val editTxtTelephoneNumberSignUp = findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
        val editTxtEmailAddressSignUp = findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

        val btnNextSignUpSetPIN = findViewById<Button>(R.id.btnNextSignUpSetPIN)
        btnNextSignUpSetPIN.setOnClickListener {
            val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim()
            val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

            val intent = Intent(this, SignUpSetPINActivity::class.java)
            if (editTxtTelephoneNumberSignUp.text.toString().trim() == ""){
                Toast.makeText(this,"Telephone Number is required", Toast.LENGTH_SHORT).show()
            } else{
                intent.putExtra("emailAddress",passEmailAddress)
                intent.putExtra("telephoneNumber",passTelephoneNumber)

                startActivity(intent)

                editTxtEmailAddressSignUp.text.clear()
                editTxtTelephoneNumberSignUp.text.clear()
            }
        }
        val btnBackToJobDescription = findViewById<Button>(R.id.btnBackToJobDescription)
        btnBackToJobDescription.setOnClickListener {
            val intent = Intent(this, SignUpJobDescriptionActivity::class.java)
            startActivity(intent)
        }
    }
}

