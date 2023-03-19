package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class LeaveSchedule : Fragment() {
    private lateinit var spinnerLeaveScheduleEmployeeName: Spinner
    private lateinit var editTxtDateStartLeaveSchedule: EditText
    private lateinit var editTxtDateEndLeaveSchedule: EditText
    private lateinit var btnGenerateLeaveSchedule: Button
    private lateinit var employeeList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_leaveschedule, container, false)

        employeeList = ArrayList()
        employeeList.add("")

        val db = DBHelper(v.context, null)
        val cursor = db.getLoginDetails()

        if (cursor!!.moveToFirst()) {
            do {
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if (nameCheck.toString().isNotEmpty()) {
                    val name = nameCheck.toString()
                    employeeList.add(name)
                    employeeList.add("")
                }
            } while (cursor.moveToNext())
        }
        cursor.close()

        spinnerLeaveScheduleEmployeeName = v.findViewById(R.id.spinnerLeaveScheduleEmployeeName)
        if (spinnerLeaveScheduleEmployeeName != null) {
            val adapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, employeeList)
            spinnerLeaveScheduleEmployeeName.adapter = adapter

            spinnerLeaveScheduleEmployeeName.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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

        editTxtDateStartLeaveSchedule = v.findViewById(R.id.editTxtDateStartLeaveSchedule)
        editTxtDateStartLeaveSchedule.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String =
                        String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateStartLeaveSchedule.setText(date)
                },
                year1,
                month,
                day
            )

            datePickerDialog.show()
        }

        editTxtDateEndLeaveSchedule = v.findViewById(R.id.editTxtDateEndLeaveSchedule)
        editTxtDateEndLeaveSchedule.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String =
                        String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateEndLeaveSchedule.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnGenerateLeaveSchedule = v.findViewById(R.id.btnGenerateLeaveSchedule)
        btnGenerateLeaveSchedule.setOnClickListener {
            val intent = Intent(v.context,LeaveScheduleView2Activity::class.java)

            val passStartDate = editTxtDateStartLeaveSchedule.text.toString()
            val passEndDate = editTxtDateEndLeaveSchedule.text.toString()
            val passName = spinnerLeaveScheduleEmployeeName.selectedItem.toString()

            intent.putExtra("StartDate", passStartDate)
            intent.putExtra("EndDate", passEndDate)
            intent.putExtra("FullName", passName)

            editTxtDateStartLeaveSchedule.text.clear()
            editTxtDateEndLeaveSchedule.text.clear()

            startActivity(intent)
            Toast.makeText(v.context, "GENERATING LEAVE SCHEDULE...", Toast.LENGTH_SHORT).show()
        }
        return v
    }
}