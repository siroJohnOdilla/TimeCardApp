package com.kronos.timecardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBackToLoginPage = findViewById<Button>(R.id.btnBackToLoginPage)
        btnBackToLoginPage.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val btnAdminSignUp = findViewById<Button>(R.id.btnAdminSignUp)
        btnAdminSignUp.setOnClickListener {
            val accountTag: String = "Administrator"
            val intent = Intent(this, SignUpNameActivity::class.java)
            intent.putExtra("account_type",accountTag)
            startActivity(intent)
        }
        val btnUserSignUp = findViewById<Button>(R.id.btnUserSignUp)
        btnUserSignUp.setOnClickListener {
            val accountTag: String = "User"
            val intent = Intent(this, SignUpNameActivity::class.java)
            intent.putExtra("account_type",accountTag)
            startActivity(intent)
        }
    }



}