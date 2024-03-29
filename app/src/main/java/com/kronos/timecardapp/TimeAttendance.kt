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

class TimeAttendance : Fragment(){
    private lateinit var spinnerTimeAttendanceEmployeeName: AutoCompleteTextView
    private lateinit var editTxtDateStartTimeAttendance: EditText
    private lateinit var editTxtDateEndTimeAttendance: EditText
    private lateinit var btnGenerateTimeAttendance: Button
    private lateinit var employeeList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_timeattendance, container, false)

        employeeList = ArrayList()

        val db = DBHelper(v.context, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if(nameCheck.toString().isNotEmpty()){
                    val name = nameCheck.toString()
                    employeeList.add(name)
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(v.context, R.layout.dropdown_item, employeeList)

        spinnerTimeAttendanceEmployeeName = v.findViewById(R.id.spinnerTimeAttendanceEmployeeName)
        spinnerTimeAttendanceEmployeeName.setAdapter(adapter)

        editTxtDateStartTimeAttendance = v.findViewById(R.id.editTxtDateStartTimeAttendance)
        editTxtDateStartTimeAttendance.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateStartTimeAttendance.setText(date)
                },
                year1,
                month,
                day
            )

            datePickerDialog.show()
        }

        editTxtDateEndTimeAttendance = v.findViewById(R.id.editTxtDateEndTimeAttendance)
        editTxtDateEndTimeAttendance.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateEndTimeAttendance.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnGenerateTimeAttendance = v.findViewById(R.id.btnGenerateTimeAttendance)
        btnGenerateTimeAttendance.setOnClickListener {

            val intent = Intent(v.context,TimeAttendanceViewActivity::class.java)

            val passStartDate = editTxtDateStartTimeAttendance.text.toString()
            val passEndDate = editTxtDateEndTimeAttendance.text.toString()
            val passName = spinnerTimeAttendanceEmployeeName.text.toString()

            intent.putExtra("StartDate",passStartDate)
            intent.putExtra("EndDate",passEndDate)
            intent.putExtra("FullName",passName)

            editTxtDateStartTimeAttendance.text.clear()
            editTxtDateEndTimeAttendance.text.clear()

            startActivity(intent)
            Toast.makeText(v.context,"GENERATING ATTENDANCE RECORD...",Toast.LENGTH_SHORT).show()
        }
        return v
    }
}
