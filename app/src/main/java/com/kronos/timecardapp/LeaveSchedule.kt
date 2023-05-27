package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class LeaveSchedule : Fragment() {
    private lateinit var spinnerLeaveScheduleEmployeeName: AutoCompleteTextView
    private lateinit var editTxtDateStartLeaveSchedule: EditText
    private lateinit var editTxtDateEndLeaveSchedule: EditText
    private lateinit var btnGenerateLeaveSchedule: Button
    private lateinit var applyLeaveBtn: FloatingActionButton
    private lateinit var employeeList: ArrayList<String>
    private lateinit var employeeNameList: ArrayList<String>
    private lateinit var relieverNameList: ArrayList<String>
    private lateinit var noOfDays: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_leaveschedule, container, false)

        employeeList = ArrayList()
        employeeNameList = ArrayList()
        relieverNameList = ArrayList()

        applyLeaveBtn = v.findViewById(R.id.applyLeaveBtn)
        applyLeaveBtn.setOnClickListener {
            val dialog = BottomSheetDialog(v.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottomsheet_applyleave)

            val db = DBHelper(v.context, null)
            val cursor = db.getLoginDetails()

            if(cursor!!.moveToFirst()){
                do{
                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                    if(namePrint.toString().isNotEmpty()){
                        val name = namePrint.toString()

                        employeeNameList.add(name)
                        relieverNameList.add(name)
                    }
                } while(cursor.moveToNext())
            }
            cursor.close()

            val adapter = ArrayAdapter(v.context, R.layout.dropdown_item, employeeNameList)
            val spinnerEmployeeName = dialog.findViewById<AutoCompleteTextView>(R.id.spinnerEmployeeName)
            if (spinnerEmployeeName != null) {
                spinnerEmployeeName.setAdapter(adapter)
            }

            val adapter1 = ArrayAdapter(v.context, R.layout.dropdown_item, relieverNameList)
            val spinnerRelieverNames = dialog.findViewById<AutoCompleteTextView>(R.id.spinnerRelieverNames)
            if (spinnerRelieverNames != null) {
                spinnerRelieverNames.setAdapter(adapter1)
            }

            val leave = resources.getStringArray(R.array.Leave)
            val adapter2 = ArrayAdapter(v.context, R.layout.dropdown_item, leave)
            val spinnerLeaveSelection = dialog.findViewById<AutoCompleteTextView>(R.id.spinnerLeaveSelection)
            if (spinnerLeaveSelection != null) {
                spinnerLeaveSelection.setAdapter(adapter2)
            }

            val editTxtStartDate = dialog.findViewById<EditText>(R.id.editTxtStartDate)
            if (editTxtStartDate != null) {
                editTxtStartDate.setOnClickListener {
                    val c = Calendar.getInstance()

                    val year1 = c.get(Calendar. YEAR)
                    val month = c.get(Calendar. MONTH)
                    val day = c.get(Calendar. DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(
                        v.context,
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
            }
            val editTxtEndDate = dialog.findViewById<EditText>(R.id.editTxtEndDate)
            if (editTxtEndDate != null) {
                editTxtEndDate.setOnClickListener {
                    val c = Calendar.getInstance()

                    val year1 = c.get(Calendar. YEAR)
                    val month = c.get(Calendar. MONTH)
                    val day = c.get(Calendar. DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(
                        v.context,
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
            }
            val btnCancelApproveLeave = dialog.findViewById<ImageView>(R.id.btnCancelApproveLeave)
            if (btnCancelApproveLeave != null) {
                btnCancelApproveLeave.setOnClickListener {
                    dialog.dismiss()
                }
            }
            val btnNextApproveLeave = dialog.findViewById<Button>(R.id.btnNextApproveLeave)
            if (btnNextApproveLeave != null) {
                btnNextApproveLeave.setOnClickListener {
                    val bundle = arguments
                    val nameCheckPass = bundle!!.getString("nameToCheck").toString()

                    if (spinnerEmployeeName != null) {
                        if(spinnerEmployeeName.text.toString() != nameCheckPass || spinnerEmployeeName.text.toString().isEmpty()){
                            Toast.makeText(v.context,"PLEASE SELECT YOUR ACCOUNT NAME",Toast.LENGTH_SHORT).show()
                        } else if (spinnerLeaveSelection != null) {
                            if(spinnerLeaveSelection.text.toString().isEmpty()){
                                Toast.makeText(v.context,"SELECT YOUR LEAVE TYPE",Toast.LENGTH_SHORT).show()
                            } else if (editTxtStartDate != null) {
                                if(editTxtStartDate.text.toString().isEmpty()){
                                    Toast.makeText(v.context,"SELECT START DATE",Toast.LENGTH_SHORT).show()
                                } else if (editTxtEndDate != null) {
                                    if(editTxtEndDate.text.toString().isEmpty()){
                                        Toast.makeText(v.context,"SELECT RETURN DATE",Toast.LENGTH_SHORT).show()
                                    } else if (spinnerRelieverNames != null) {
                                        if(spinnerRelieverNames.text.toString().isEmpty()){
                                            Toast.makeText(v.context,"SELECT YOUR RELIEVER NAME",Toast.LENGTH_SHORT).show()
                                        } else if(spinnerEmployeeName.text.toString() == spinnerRelieverNames.text.toString()){
                                            Toast.makeText(v.context,"YOU CANT BE YOUR OWN RELIEVER",Toast.LENGTH_SHORT).show()
                                        } else if(editTxtStartDate.text.toString().isNotEmpty() && editTxtEndDate.text.toString().isNotEmpty() && spinnerEmployeeName.text.toString() == nameCheckPass){
                                            val startDate = editTxtStartDate.text.toString()
                                            val endDate = editTxtEndDate.text.toString()

                                            val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                                            val d1: Date = makeDateFormat.parse(startDate) as Date
                                            val d2: Date = makeDateFormat.parse(endDate) as Date

                                            val difference: Long = abs(d2.time - d1.time)

                                            val differenceDays = (difference / (24*60*60*1000)) % 365
                                            noOfDays = differenceDays.toString()

                                            val dialog1 = BottomSheetDialog(v.context)
                                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            dialog1.setContentView(R.layout.bottomsheet_approveleave)

                                            val name = spinnerEmployeeName.text.toString()
                                            val leaveType = spinnerLeaveSelection.text.toString()
                                            val dateFrom = editTxtStartDate.text.toString()
                                            val dateTo = editTxtEndDate.text.toString()
                                            val noOfDays = noOfDays
                                            val reliever = spinnerRelieverNames.text.toString()

                                            val txtViewLeaveName = dialog1.findViewById<TextView>(R.id.txtViewLeaveName)
                                            val displayName = "Name\n$name"
                                            if (txtViewLeaveName != null) {
                                                txtViewLeaveName.text = displayName
                                            }

                                            val txtViewLeaveType = dialog1.findViewById<TextView>(R.id.txtViewLeaveType)
                                            val displayLeave = "Leave\n$leaveType"
                                            if (txtViewLeaveType != null) {
                                                txtViewLeaveType.text = displayLeave
                                            }

                                            val txtViewLeaveStartDate = dialog1.findViewById<TextView>(R.id.txtViewLeaveStartDate)
                                            val displayStartDate = "Start Date\n$dateFrom"
                                            if (txtViewLeaveStartDate != null) {
                                                txtViewLeaveStartDate.text = displayStartDate
                                            }

                                            val txtViewLeaveEndDate= dialog1.findViewById<TextView>(R.id.txtViewLeaveEndDate)
                                            val displayEndDate = "Return Date\n$dateTo"
                                            if (txtViewLeaveEndDate != null) {
                                                txtViewLeaveEndDate.text = displayEndDate
                                            }

                                            val txtViewLeaveReliever = dialog1.findViewById<TextView>(R.id.txtViewLeaveReliever)
                                            val displayReliever = "Reliever\n$reliever"
                                            if (txtViewLeaveReliever != null) {
                                                txtViewLeaveReliever.text = displayReliever
                                            }

                                            val txtViewLeaveNoOfDays = dialog1.findViewById<TextView>(R.id.txtViewLeaveNoOfDays)
                                            val displayNoOfDays = "$noOfDays\nDays"
                                            if (txtViewLeaveNoOfDays != null) {
                                                txtViewLeaveNoOfDays.text = displayNoOfDays
                                            }

                                            val editTxtLeaveAdminKey = dialog1.findViewById<EditText>(R.id.editTxtLeaveAdminKey)

                                            val btnCancelApproveLeave2 = dialog1.findViewById<ImageView>(R.id.btnCancelApproveLeave2)
                                            if (btnCancelApproveLeave2 != null) {
                                                btnCancelApproveLeave2.setOnClickListener {
                                                    dialog1.dismiss()
                                                }
                                            }
                                            val btnFinishLeaveApplication = dialog1.findViewById<Button>(R.id.btnFinishLeaveApplication)
                                            if (btnFinishLeaveApplication != null) {
                                                btnFinishLeaveApplication.setOnClickListener {
                                                    val adminLeaveKey = "0ff1c3-sp4c3"

                                                    val db = DBHelper(v.context, null)
                                                    val cursor = db.getLoginDetails()

                                                    if (editTxtLeaveAdminKey != null) {
                                                        if(editTxtLeaveAdminKey.text.toString().trim().isEmpty() || editTxtLeaveAdminKey.text.toString().trim() != adminLeaveKey ){
                                                            Toast.makeText(v.context,"ENTER VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
                                                        }  else if(editTxtLeaveAdminKey.text.toString().trim() == adminLeaveKey){
                                                            if(cursor!!.moveToFirst()){
                                                                do{
                                                                    val nameCheck1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                                    val accountTagCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                                                                    if(accountTagCheck.toString() == "ADMINISTRATOR"){
                                                                        val authorizedBy = nameCheck1.toString()

                                                                        val db1 = DBHelper3(v.context, null)
                                                                        db1.addDetails(name, leaveType, dateFrom, dateTo, noOfDays, reliever, authorizedBy)

                                                                        dialog1.dismiss()

                                                                        Toast.makeText(v.context,"LEAVE APPLICATION SUCCESSFUL",Toast.LENGTH_SHORT).show()
                                                                        break
                                                                    }
                                                                } while(cursor.moveToNext())
                                                            }
                                                            cursor.close()
                                                        }
                                                    }
                                                }
                                            }
                                            dialog1.show()
                                            dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                            dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                            dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                            dialog1.window!!.setGravity(Gravity.BOTTOM)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            dialog.show()
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }

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