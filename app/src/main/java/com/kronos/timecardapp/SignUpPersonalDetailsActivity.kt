package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.app.DatePickerDialog
class SignUpPersonalDetailsActivity : AppCompatActivity(){
    private lateinit var editTxtDateOfBirthSignUp : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signuppersonaldetails)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle : Bundle? = intent.extras

        val name1 = bundle!!.getString("nameKey")
        val accountTag2 = bundle.getString("accountTagKey1")

        val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, genders)

        val spinnerGenderSignUp = findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
        spinnerGenderSignUp.setAdapter(adapter)

        val editTxtNationalIDNo = findViewById<EditText>(R.id.editTxtNationalIDNo)
        editTxtDateOfBirthSignUp = findViewById(R.id.editTxtDateOfBirthSignUp)
        editTxtDateOfBirthSignUp.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateOfBirthSignUp.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        val btnNextSignUpJobDescription = findViewById<Button>(R.id.btnNextSignUpJobDescription)
        btnNextSignUpJobDescription.setOnClickListener {

            val intent = Intent(this,SignUpJobDescriptionActivity::class.java)

            if(spinnerGenderSignUp.text.toString().isEmpty()){
                Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
            } else if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
            } else if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
            } else{
                val passGender = spinnerGenderSignUp.text.toString()
                val passNationalId = editTxtNationalIDNo.text.toString().trim()
                val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                intent.putExtra("accountTagKey2",accountTag2)
                intent.putExtra("nameKey1",name1)
                intent.putExtra("genderKey",passGender)
                intent.putExtra("nationalIdKey",passNationalId)
                intent.putExtra("dateOfBirthKey",passDateOfBirth)

                spinnerGenderSignUp.text.clear()
                editTxtNationalIDNo.text.clear()
                editTxtDateOfBirthSignUp.text.clear()

                startActivity(intent)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}