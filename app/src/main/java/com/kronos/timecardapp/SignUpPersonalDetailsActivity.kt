package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.app.DatePickerDialog
class SignUpPersonalDetailsActivity : AppCompatActivity(){
    private lateinit var editTxtDateOfBirthSignUp : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signuppersonaldetails)

        val bundle : Bundle? = intent.extras

        val name1 = bundle!!.getString("nameKey")
        val accountTag2 = bundle.getString("accountTagKey1")

        val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml

        val spinnerGenderSignUp = findViewById<Spinner>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
        if (spinnerGenderSignUp != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            spinnerGenderSignUp.adapter = adapter

            spinnerGenderSignUp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@SignUpPersonalDetailsActivity,getString(R.string.selected_item) + " " + " " + genders[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
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
                    val date = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
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

            if ((editTxtNationalIDNo.text.toString().trim().uppercase() == "")){
                Toast.makeText(this,"National ID is required", Toast.LENGTH_SHORT).show()
            } else if ((editTxtDateOfBirthSignUp.text.toString().trim().uppercase() == "")){
                Toast.makeText(this,"Date of Birth is required", Toast.LENGTH_SHORT).show()
            } else{
                val passGender = spinnerGenderSignUp.selectedItem.toString().trim().uppercase()
                val passNationalId = editTxtNationalIDNo.text.toString().trim().uppercase()
                val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString().trim().uppercase()

                intent.putExtra("accountTagKey2",accountTag2)
                intent.putExtra("nameKey1",name1)
                intent.putExtra("genderKey",passGender)
                intent.putExtra("nationalIdKey",passNationalId)
                intent.putExtra("dateOfBirthKey",passDateOfBirth)

                startActivity(intent)
            }
        }
        val btnBackToSignUpName = findViewById<Button>(R.id.btnBackToSignUpName)
        btnBackToSignUpName.setOnClickListener {
            val intent = Intent(this,SignUpNameActivity::class.java)
            startActivity(intent)
        }

    }
}