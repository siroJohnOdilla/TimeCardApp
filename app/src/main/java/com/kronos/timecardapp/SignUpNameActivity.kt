package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpNameActivity : AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupname)

        val bundle : Bundle? = intent.extras
        val msg = bundle!!.getString("account_type")

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

        val txtAccountTypeSignUp = findViewById<TextView>(R.id.txtAccountTypeSignUp)
        txtAccountTypeSignUp.text = msg

        val editTxtFirstNameSignUp = findViewById<EditText>(R.id.editTxtFirstNameSignUp)
        val editTxtMiddleNameSignUp = findViewById<EditText>(R.id.editTxtMiddleNameSignUp)
        val editTxtLastNameSignUp = findViewById<EditText>(R.id.editTxtLastNameSignUp)

        val btnNextSignUpPersonalDetails = findViewById<Button>(R.id.btnNextSignUpPersonalDetails)
        btnNextSignUpPersonalDetails.setOnClickListener{
            val passName: String = ((editTxtFirstNameSignUp.text.toString().trim().uppercase()) + "" + (editTxtMiddleNameSignUp.text.toString().trim().uppercase()) + "" + (editTxtLastNameSignUp.text.toString().trim().uppercase()))
            val passAccountTag: String = msg.toString().trim().uppercase()

            val intent = Intent(this,SignUpPersonalDetailsActivity::class.java)
            if((editTxtFirstNameSignUp.text.toString().trim() == "")){
                Toast.makeText(this, "First Name is empty", Toast.LENGTH_SHORT).show()
            } else if ((editTxtMiddleNameSignUp.text.toString().trim() == "")){
                Toast.makeText(this, "Middle Name is empty", Toast.LENGTH_SHORT).show()
            } else if ((editTxtLastNameSignUp.text.toString().trim() == "")){
                Toast.makeText(this, "Last Name is empty", Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra("name",passName)
                intent.putExtra("accountTag",passAccountTag)

                startActivity(intent)

                editTxtFirstNameSignUp.text.clear()
                editTxtMiddleNameSignUp.text.clear()
                editTxtLastNameSignUp.text.clear()
            }
        }
        val btnBackToAccountSelection = findViewById<Button>(R.id.btnBackToAccountSelection)
        btnBackToAccountSelection.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}