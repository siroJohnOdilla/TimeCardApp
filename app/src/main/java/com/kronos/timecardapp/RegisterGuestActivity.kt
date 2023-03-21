package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RegisterGuestActivity : AppCompatActivity() {
    private lateinit var editTxtNameVisitor: EditText
    private lateinit var editTxtNationalIDVisitor: EditText
    private lateinit var editTxtTelephoneNumberVisitor: EditText
    private lateinit var editTxtCompanyVisitor: EditText
    private lateinit var editTxtCompanyHostVisitor: EditText
    private lateinit var editTxtNatureOfVisit: EditText
    private lateinit var btnRegisterVisitor: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerguest)

        val bundle : Bundle? = intent.extras

        val passName = bundle!!.getString("nameToCheck").toString()

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        editTxtNameVisitor = findViewById(R.id.editTxtNameVisitor)
        editTxtNationalIDVisitor = findViewById(R.id.editTxtNationalIDVisitor)
        editTxtTelephoneNumberVisitor = findViewById(R.id.editTxtTelephoneNumberVisitor)
        editTxtCompanyVisitor = findViewById(R.id.editTxtCompanyVisitor)
        editTxtCompanyHostVisitor = findViewById(R.id.editTxtCompanyHostVisitor)
        editTxtNatureOfVisit = findViewById(R.id.editTxtNatureOfVisit)

        btnRegisterVisitor = findViewById(R.id.btnRegisterVisitor)
        btnRegisterVisitor.setOnClickListener {
            if(editTxtNameVisitor.text.toString().trim().isEmpty()){
                Toast.makeText(this,"VISITOR NAME REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtNationalIDVisitor.text.toString().trim().isEmpty()){
                Toast.makeText(this,"NATIONAL ID REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtTelephoneNumberVisitor.text.toString().trim().isEmpty()){
                Toast.makeText(this,"TELEPHONE NO. REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtCompanyVisitor.text.toString().trim().isEmpty()){
                Toast.makeText(this,"COMPANY REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtCompanyHostVisitor.text.toString().trim().isEmpty()){
                Toast.makeText(this,"CONTACT PERSON REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtNatureOfVisit.text.toString().trim().isEmpty()){
                Toast.makeText(this,"NATURE OF VISIT REQUIRED",Toast.LENGTH_SHORT).show()
            } else{
                val makeDateFormat1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = makeDateFormat1.format(Date())

                val name = editTxtNameVisitor.text.toString().trim().uppercase()
                val nationalId = editTxtNationalIDVisitor.text.toString().trim()
                val telephoneNumber = editTxtTelephoneNumberVisitor.text.toString().trim()
                val company = editTxtCompanyVisitor.text.toString().trim().uppercase()
                val companyHost = editTxtCompanyHostVisitor.text.toString().trim().uppercase()
                val natureOfVisit = editTxtNatureOfVisit.text.toString().trim().uppercase()

                val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val timeIn = makeDateFormat2.format(Date())
                val authorizedTimeIn = passName

                val timeOut = ""
                val authorizedTimeOut = ""

                val db = DBHelper4(this, null)
                db.addVisitor(date, name, nationalId, telephoneNumber, company, companyHost, natureOfVisit, timeIn, authorizedTimeIn, timeOut, authorizedTimeOut)

                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"WELCOME $name\nTIME IN: $timeIn",Toast.LENGTH_SHORT).show()

            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}