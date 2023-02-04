package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTxtFullNameNationalIdLogIn = findViewById<EditText>(R.id.editTxtFullNameNationalIdLogIn)
        val editTxtPINLogIn = findViewById<EditText>(R.id.editTxtPINLogIn)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {

            val db = DBHelper(this,null)

            val cursor = db.getLoginDetails()
            cursor!!.moveToFirst()

            while(cursor.moveToNext()){
                val nameLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val nationalIdLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val pinLogIn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))

                if(editTxtFullNameNationalIdLogIn.text.toString().trim().uppercase() == nameLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString()){
                    val intent = Intent(this,HomePageActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"LOGIN SUCCESSFUL; WELCOME ${editTxtFullNameNationalIdLogIn.text.toString().uppercase()}",Toast.LENGTH_SHORT).show()
                    break
                } else if (editTxtFullNameNationalIdLogIn.text.toString().trim() == nationalIdLogIn.toString() && editTxtPINLogIn.text.toString().trim() == pinLogIn.toString()){
                    val intent = Intent(this,HomePageActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"LOGIN SUCCESSFUL; WELCOME ID: ${editTxtFullNameNationalIdLogIn.text.toString().uppercase()}",Toast.LENGTH_SHORT).show()
                    break
                } /*else {
                    Toast.makeText(this,"LOGIN FAILED; INVALID CREDENTIALS",Toast.LENGTH_SHORT).show()
                }*/
            }
            cursor.close()
        }
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}