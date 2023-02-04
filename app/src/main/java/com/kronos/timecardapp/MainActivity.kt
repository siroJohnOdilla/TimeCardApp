package com.kronos.timecardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var displayAccountTag: String

        val btnBackToLoginPage = findViewById<Button>(R.id.btnBackToLoginPage)
        btnBackToLoginPage.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)

            startActivity(intent)
        }

        val btnAdminSignUp = findViewById<Button>(R.id.btnAdminSignUp)
        btnAdminSignUp.setOnClickListener {
            displayAccountTag = "ADMINISTRATOR"
            val passAccountTag = displayAccountTag
            val intent = Intent(this, SignUpNameActivity::class.java)
            intent.putExtra("accountTagKey",passAccountTag)

            startActivity(intent)
        }
        val btnUserSignUp = findViewById<Button>(R.id.btnUserSignUp)
        btnUserSignUp.setOnClickListener {
            displayAccountTag = "USER"
            val passAccountTag = displayAccountTag
            val intent = Intent(this, SignUpNameActivity::class.java)
            intent.putExtra("accountTagKey",passAccountTag)

            startActivity(intent)
        }
    }



}