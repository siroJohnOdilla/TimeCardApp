package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class ApplyLeaveActivity : AppCompatActivity() {
    private lateinit var employeeNameList: ArrayList<String>
    private lateinit var relieverNameList: ArrayList<String>
    private lateinit var noOfDays: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applyleave)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        employeeNameList = ArrayList()
        employeeNameList.add("")
        relieverNameList = ArrayList()
        relieverNameList.add("")

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
               val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if(namePrint.toString().isNotEmpty()){
                    val name = namePrint.toString()

                    employeeNameList.add(name)
                    employeeNameList.add("")
                    relieverNameList.add(name)
                    relieverNameList.add("")
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        val spinnerEmployeeName = findViewById<Spinner>(R.id.spinnerEmployeeName)
        if (spinnerEmployeeName != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, employeeNameList)
            spinnerEmployeeName.adapter = adapter

            spinnerEmployeeName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,getString(R.string.selected_item) + " " + " " + genders[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val spinnerRelieverNames = findViewById<Spinner>(R.id.spinnerRelieverNames)
        if (spinnerRelieverNames != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, relieverNameList)
            spinnerRelieverNames.adapter = adapter

            spinnerRelieverNames.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,getString(R.string.selected_item) + " " + " " + genders[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val spinnerLeaveSelection = findViewById<Spinner>(R.id.spinnerLeaveSelection)
        val leave = resources.getStringArray(R.array.Leave)
        if (spinnerLeaveSelection != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, leave)
            spinnerLeaveSelection.adapter = adapter

            spinnerLeaveSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,getString(R.string.selected_item) + " " + " " + genders[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val editTxtStartDate = findViewById<EditText>(R.id.editTxtStartDate)
        editTxtStartDate.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtStartDate.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }
        val editTxtEndDate = findViewById<EditText>(R.id.editTxtEndDate)
        editTxtEndDate.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtEndDate.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        val btnNextApproveLeave = findViewById<Button>(R.id.btnNextApproveLeave)
        btnNextApproveLeave.setOnClickListener {
            val intent1 = intent
            val nameCheckPass = intent1.getStringExtra("nameToCheck").toString()

            if(spinnerEmployeeName.selectedItem.toString() != nameCheckPass || spinnerEmployeeName.selectedItem.toString().isEmpty()){
                Toast.makeText(this,"PLEASE SELECT YOUR ACCOUNT NAME",Toast.LENGTH_SHORT).show()
            } else if(spinnerLeaveSelection.selectedItem.toString().isEmpty()){
                Toast.makeText(this,"SELECT YOUR LEAVE TYPE",Toast.LENGTH_SHORT).show()
            } else if(editTxtStartDate.text.toString().isEmpty()){
                Toast.makeText(this,"SELECT START DATE",Toast.LENGTH_SHORT).show()
            } else if(editTxtEndDate.text.toString().isEmpty()){
                Toast.makeText(this,"SELECT END DATE",Toast.LENGTH_SHORT).show()
            } else if(spinnerRelieverNames.selectedItem.toString().isEmpty()){
                Toast.makeText(this,"SELECT YOUR RELIEVER NAME",Toast.LENGTH_SHORT).show()
            } else if(spinnerEmployeeName.selectedItem.toString() == spinnerRelieverNames.selectedItem.toString()){
                Toast.makeText(this,"YOU CANT BE YOUR OWN RELIEVER",Toast.LENGTH_SHORT).show()
            } else if(editTxtStartDate.text.toString().isNotEmpty() && editTxtEndDate.text.toString().isNotEmpty() && spinnerEmployeeName.selectedItem.toString() == nameCheckPass){
                val startDate = editTxtStartDate.text.toString()
                val endDate = editTxtEndDate.text.toString()

                val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                val d1: Date = makeDateFormat.parse(startDate) as Date
                val d2: Date = makeDateFormat.parse(endDate) as Date

                val difference: Long = abs(d2.time - d1.time)

                val differenceDays = (difference / (24*60*60*1000)) % 365
                noOfDays = differenceDays.toString()

                val passName = spinnerEmployeeName.selectedItem.toString()
                val passLeave = spinnerLeaveSelection.selectedItem.toString()
                val passStartDate = editTxtStartDate.text.toString()
                val passEndDate = editTxtEndDate.text.toString()
                val passReliever = spinnerRelieverNames.selectedItem.toString()
                val passNoOfDays = noOfDays

                val intent = Intent(this,ApproveLeaveActivity::class.java)

                intent.putExtra("Name",passName)
                intent.putExtra("Leave",passLeave)
                intent.putExtra("StartDate",passStartDate)
                intent.putExtra("EndDate",passEndDate)
                intent.putExtra("Reliever",passReliever)
                intent.putExtra("NoOfDays",passNoOfDays)

                startActivity(intent)
                Toast.makeText(this,"WAITING APPROVAL...",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}