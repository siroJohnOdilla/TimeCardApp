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
    private lateinit var spinnerLeaveScheduleEmployeeName: AutoCompleteTextView
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

        val db = DBHelper(v.context, null)
        val cursor = db.getLoginDetails()

        if (cursor!!.moveToFirst()) {
            do {
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if (nameCheck.toString().isNotEmpty()) {
                    val name = nameCheck.toString()
                    employeeList.add(name)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(v.context, R.layout.dropdown_item, employeeList)

        spinnerLeaveScheduleEmployeeName = v.findViewById(R.id.spinnerLeaveScheduleEmployeeName)
        spinnerLeaveScheduleEmployeeName.setAdapter(adapter)

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
            val passName = spinnerLeaveScheduleEmployeeName.text.toString()

            intent.putExtra("StartDate", passStartDate)
            intent.putExtra("EndDate", passEndDate)
            intent.putExtra("FullName", passName)

            editTxtDateStartLeaveSchedule.text.clear()
            editTxtDateEndLeaveSchedule.text.clear()
            spinnerLeaveScheduleEmployeeName.text.clear()

            startActivity(intent)
            Toast.makeText(v.context, "OPENING LEAVE SCHEDULE...", Toast.LENGTH_SHORT).show()
        }
        return v
    }
}