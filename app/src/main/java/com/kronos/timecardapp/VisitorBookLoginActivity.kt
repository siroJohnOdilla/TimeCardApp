package com.kronos.timecardapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class VisitorBookLoginActivity : AppCompatActivity(){
    private lateinit var spinnerVisitorBook: AutoCompleteTextView
    private lateinit var editTxtDateStartVisitorBook: EditText
    private lateinit var editTxtDateEndVisitorBook: EditText
    private lateinit var addVisitorBtn: FloatingActionButton
    private lateinit var btnLogOutCode: FloatingActionButton
    private lateinit var btnGenerateVisitorBook: Button
    private lateinit var visitorList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitorbooklogin)

        val actionBar = supportActionBar
        actionBar!!.title = "Visitor Book"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,android.Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(this as Activity, arrayOf(android.Manifest.permission.SEND_SMS), 1)
            } else{
                ActivityCompat.requestPermissions(this as Activity,arrayOf(android.Manifest.permission.SEND_SMS), 1)
            }
        }
        btnLogOutCode = findViewById(R.id.btnLogOutCode)
        btnLogOutCode.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this,R.style.RoundedMaterialDialog).setView(R.layout.dialog_profileaccess).show()

            val txtViewDisplayMessageProfileAccess = dialog.findViewById<TextView>(R.id.txtViewDisplayMessageProfileAccess)
            val displayName = " "
            if (txtViewDisplayMessageProfileAccess != null) {
                txtViewDisplayMessageProfileAccess.text = displayName
            }

            val editTxtEnterPINProfileAccess = dialog.findViewById<EditText>(R.id.editTxtEnterPINProfileAccess)

            val btnCancelPINProfileAccess = dialog.findViewById<ImageView>(R.id.btnCancelPINProfileAccess)
            if (btnCancelPINProfileAccess != null) {
                btnCancelPINProfileAccess.setOnClickListener {
                    dialog.dismiss()
                }
            }
            val btnEnterPINProfileAccess = dialog.findViewById<Button>(R.id.btnEnterPINProfileAccess)
            if (btnEnterPINProfileAccess != null) {
                btnEnterPINProfileAccess.setOnClickListener {
                    val code = editTxtEnterPINProfileAccess?.text.toString()

                    val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val date = makeDateFormat.format(Date())

                    val db = DBHelper4(this, null)
                    val cursor = db.getDetails()

                    if(cursor!!.moveToFirst()){
                        do{
                            val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))
                            val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.DATE_COL))
                            val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.ID_COL))
                            val logOutCodePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.LOG_OUT_CODE))

                            if(date == datePrint.toString() && code == logOutCodePrint.toString()){
                                val id = idPrint.toLong()

                                val name = namePrint.toString()

                                val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                val timeOut = makeDateFormat2.format(Date())

                                db.updateVisitor(id, timeOut)
                                dialog.dismiss()

                                val dialog1 = MaterialAlertDialogBuilder(this,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmattendance).show()

                                val attendanceMessage = dialog1.findViewById<TextView>(R.id.attendanceMessage)
                                val displayMessage = "LOG OUT SUCCESSFUL!"
                                if (attendanceMessage != null) {
                                    attendanceMessage.text = displayMessage
                                }
                                val txtAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtAttendanceConfirm)
                                if (txtAttendanceConfirm != null) {
                                    val displayName = "Name\n$name"
                                    txtAttendanceConfirm.text = displayName
                                }
                                val txtTimeAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                if (txtTimeAttendanceConfirm != null) {
                                    val displayTime = "Time out\n$timeOut"
                                    txtTimeAttendanceConfirm.text = displayTime
                                }
                                val btnOkAttendanceConfirm = dialog1.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                if (btnOkAttendanceConfirm != null) {
                                    btnOkAttendanceConfirm.setOnClickListener {
                                        dialog1.dismiss()
                                    }
                                }

                            }

                        } while(cursor.moveToNext())
                    }
                    cursor.close()
                }
            }
        }

        addVisitorBtn = findViewById(R.id.addVisitorBtn)
        addVisitorBtn.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottomsheet_registerguest)

            val editTxtNameVisitor = dialog.findViewById<EditText>(R.id.editTxtNameVisitor)
            val editTxtNationalIDVisitor = dialog.findViewById<EditText>(R.id.editTxtNationalIDVisitor)
            val editTxtTelephoneNumberVisitor = dialog.findViewById<EditText>(R.id.editTxtTelephoneNumberVisitor)
            val editTxtCompanyVisitor = dialog.findViewById<EditText>(R.id.editTxtCompanyVisitor)
            val editTxtCompanyHostVisitor = dialog.findViewById<EditText>(R.id.editTxtCompanyHostVisitor)
            val editTxtNatureOfVisit = dialog.findViewById<EditText>(R.id.editTxtNatureOfVisit)

            val btnRegisterVisitor = dialog.findViewById<Button>(R.id.btnRegisterVisitor)
            if (btnRegisterVisitor != null) {
                btnRegisterVisitor.setOnClickListener {
                    if (editTxtNameVisitor != null) {
                        if(editTxtNameVisitor.text.toString().trim().isEmpty()){
                            Toast.makeText(this,"VISITOR NAME REQUIRED", Toast.LENGTH_SHORT).show()
                        } else if (editTxtNationalIDVisitor != null) {
                            if(editTxtNationalIDVisitor.text.toString().trim().isEmpty()){
                                Toast.makeText(this,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                            } else if (editTxtTelephoneNumberVisitor != null) {
                                if(editTxtTelephoneNumberVisitor.text.toString().trim().isEmpty()){
                                    Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                } else if (editTxtCompanyVisitor != null) {
                                    if(editTxtCompanyVisitor.text.toString().trim().isEmpty()){
                                        Toast.makeText(this,"COMPANY REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else if (editTxtCompanyHostVisitor != null) {
                                        if(editTxtCompanyHostVisitor.text.toString().trim().isEmpty()){
                                            Toast.makeText(this,"CONTACT PERSON REQUIRED",
                                                Toast.LENGTH_SHORT).show()
                                        } else if (editTxtNatureOfVisit != null) {
                                            if(editTxtNatureOfVisit.text.toString().trim().isEmpty()){
                                                Toast.makeText(this,"NATURE OF VISIT REQUIRED",
                                                    Toast.LENGTH_SHORT).show()
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

                                                val timeOut = ""

                                                val random = Random()
                                                val logOutCode = (random.nextInt(9000)+1000).toString()

                                                val db = DBHelper4(this, null)
                                                db.addVisitor(date, name, nationalId, telephoneNumber, company, companyHost, natureOfVisit, timeIn, timeOut, logOutCode)

                                                editTxtNameVisitor.text.clear()
                                                editTxtNationalIDVisitor.text.clear()
                                                editTxtTelephoneNumberVisitor.text.clear()
                                                editTxtCompanyVisitor.text.clear()
                                                editTxtCompanyHostVisitor.text.clear()
                                                editTxtNatureOfVisit.text.clear()

                                                val dialog1 = MaterialAlertDialogBuilder(this,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmattendance).show()

                                                val attendanceMessage = dialog1.findViewById<TextView>(R.id.attendanceMessage)
                                                val displayMessage = "REGISTRATION SUCCESSFUL!"
                                                if (attendanceMessage != null) {
                                                    attendanceMessage.text = displayMessage
                                                }
                                                val txtAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtAttendanceConfirm)
                                                if (txtAttendanceConfirm != null) {
                                                    val displayName = "Name\n$name"
                                                    txtAttendanceConfirm.text = displayName
                                                }
                                                val txtTimeAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                                if (txtTimeAttendanceConfirm != null) {
                                                    val displayTime = "Time in\n$timeIn"
                                                    txtTimeAttendanceConfirm.text = displayTime
                                                }
                                                val btnOkAttendanceConfirm = dialog1.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                if (btnOkAttendanceConfirm != null) {
                                                    btnOkAttendanceConfirm.setOnClickListener {
                                                        try{
                                                            val message = "Your PIN is $logOutCode"

                                                            val smsManager: SmsManager = this.getSystemService(
                                                                SmsManager::class.java)
                                                            smsManager.sendTextMessage(telephoneNumber,null,message,null,null)

                                                            Toast.makeText(this,"MESSAGE SENT",
                                                                Toast.LENGTH_SHORT).show()

                                                        } catch(e: Exception){
                                                            Toast.makeText(this,"ENTER CORRECT NUMBER",
                                                                Toast.LENGTH_SHORT).show()
                                                        }
                                                        dialog1.dismiss()
                                                    }
                                                }
                                            }
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

            val btnCancelRegisterVisitor = dialog.findViewById<ImageView>(R.id.btnCancelRegisterVisitor)
            if (btnCancelRegisterVisitor != null) {
                btnCancelRegisterVisitor.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        visitorList = ArrayList()

        val db = DBHelper4(this, null)
        val cursor = db.getDetails()

        if(cursor!!.moveToFirst()){
            do{
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))

                if(nameCheck.toString().isNotEmpty()){
                    val name = nameCheck.toString()
                    visitorList.add(name)
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, visitorList)

        spinnerVisitorBook = findViewById(R.id.spinnerVisitorBookName)
        spinnerVisitorBook.setAdapter(adapter)

        editTxtDateStartVisitorBook = findViewById(R.id.editTxtDateStartVisitorBook)
        editTxtDateStartVisitorBook.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateStartVisitorBook.setText(date)
                },
                year1,
                month,
                day
            )

            datePickerDialog.show()
        }

        editTxtDateEndVisitorBook = findViewById(R.id.editTxtDateEndVisitorBook)
        editTxtDateEndVisitorBook.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateEndVisitorBook.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnGenerateVisitorBook = findViewById(R.id.btnGenerateVisitBook)
        btnGenerateVisitorBook.setOnClickListener {

            val intent = Intent(this,VisitorBookViewActivity::class.java)

            val passStartDate = editTxtDateStartVisitorBook.text.toString()
            val passEndDate = editTxtDateEndVisitorBook.text.toString()
            val passName1 = spinnerVisitorBook.text.toString()

            intent.putExtra("StartDate",passStartDate)
            intent.putExtra("EndDate",passEndDate)
            intent.putExtra("FullName",passName1)

            editTxtDateStartVisitorBook.text.clear()
            editTxtDateEndVisitorBook.text.clear()
            spinnerVisitorBook.text.clear()

            startActivity(intent)
            Toast.makeText(this,"OPENING VISITOR'S BOOK...", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return true
    }
}