package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*
class TimeAttendance : Fragment(){
    private lateinit var editTxtFullNameNationalIdCheck: EditText
    private lateinit var editTxtDateStartTimeAttendance: EditText
    private lateinit var editTxtDateEndTimeAttendance: EditText
    private lateinit var btnGenerateTimeAttendance: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_timeattendance, container, false)

        editTxtFullNameNationalIdCheck = v.findViewById(R.id.editTxtFullNameNationalIdCheck)

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
            if(editTxtDateStartTimeAttendance.toString().isEmpty() && editTxtDateEndTimeAttendance.toString().isEmpty()){
                Toast.makeText(v.context,"DATE RANGE IS REQUIRED",Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(v.context,TimeAttendanceViewActivity::class.java)

            val passStartDate = editTxtDateStartTimeAttendance.text.toString()
            val passEndDate = editTxtDateEndTimeAttendance.text.toString()
            val passFullNameNationalId = editTxtFullNameNationalIdCheck.text.toString().trim().uppercase()

            intent.putExtra("StartDate",passStartDate)
            intent.putExtra("EndDate",passEndDate)
            intent.putExtra("FullNameNationalId",passFullNameNationalId)

            editTxtFullNameNationalIdCheck.text.clear()
            editTxtDateStartTimeAttendance.text.clear()
            editTxtDateEndTimeAttendance.text.clear()

            startActivity(intent)
            Toast.makeText(v.context,"GENERATING REPORT...",Toast.LENGTH_SHORT).show()
        }
        return v
    }
}
