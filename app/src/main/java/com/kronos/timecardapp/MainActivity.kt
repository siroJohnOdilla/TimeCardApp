package com.kronos.timecardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Choose Account"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        var displayAccountTag: String

        val editTxtAdminKey = findViewById<EditText>(R.id.editTxtAdminKey)

        val btnAdminSignUp = findViewById<Button>(R.id.btnAdminSignUp)
        btnAdminSignUp.setOnClickListener {
            val adminKeyHolder = "K1LL-81LL-V0L12"

            if(editTxtAdminKey.text.toString().trim().uppercase() == adminKeyHolder){
                displayAccountTag = "ADMINISTRATOR"
                val passAccountTag = displayAccountTag

                val intent = Intent(this, SignUpNameActivity::class.java)
                intent.putExtra("accountTagKey",passAccountTag)

                editTxtAdminKey.text.clear()

                startActivity(intent)

            } else if (editTxtAdminKey.text.toString().isEmpty()){
                Toast.makeText(this,"INSERT ADMIN KEY", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"INSERT VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
            }

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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}