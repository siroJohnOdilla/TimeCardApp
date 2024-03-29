package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class ProfileView2Activity : AppCompatActivity() {
    private lateinit var txtProfileAccount: TextView
    private lateinit var txtProfileName: TextView
    private lateinit var txtProfileGender: TextView
    private lateinit var txtProfileNationalId: TextView
    private lateinit var txtProfileDateOfBirth: TextView
    private lateinit var txtProfileEmail: TextView
    private lateinit var txtProfileTelephoneNo: TextView
    private lateinit var txtProfileCompany: TextView
    private lateinit var txtProfileSiteBranch: TextView
    private lateinit var txtProfileDepartment: TextView
    private lateinit var txtProfileJobTitle: TextView
    private lateinit var getName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileview2)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        val passName = bundle!!.getString("passName").toString()
        getName = passName

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val accountPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))
                val genderPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GENDER))
                val nationalIdPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val dateOfBirthPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE_OF_BIRTH))
                val emailPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_ADDRESS_NAME))
                val telephonePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TELEPHONE_NUMBER))
                val companyPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                val companyInitialsPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                val siteBranchPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))

                if(passName == namePrint.toString()){
                    txtProfileAccount = findViewById(R.id.txtProfileAccount)
                    val displayAccount = "Account\n$accountPrint"
                    txtProfileAccount.text = displayAccount

                    txtProfileName = findViewById(R.id.txtProfileName)
                    val displayName = "Name\n$namePrint"
                    txtProfileName.text = displayName

                    txtProfileGender = findViewById(R.id.txtProfileGender)
                    val displayGender = "Gender\n$genderPrint"
                    txtProfileGender.text = displayGender

                    txtProfileNationalId = findViewById(R.id.txtProfileNationalId)
                    val displayNationalId = "National ID No.\n$nationalIdPrint"
                    txtProfileNationalId.text = displayNationalId

                    txtProfileDateOfBirth = findViewById(R.id.txtProfileDateOfBirth)
                    val displayDateOfBirth = "Date of Birth\n$dateOfBirthPrint"
                    txtProfileDateOfBirth.text = displayDateOfBirth

                    txtProfileEmail = findViewById(R.id.txtProfileEmail)
                    val displayEmail = "E-Mail Address\n$emailPrint"
                    txtProfileEmail.text = displayEmail

                    txtProfileTelephoneNo = findViewById(R.id.txtProfilePhoneNo)
                    val displayPhoneNo = "Telephone No.\n$telephonePrint"
                    txtProfileTelephoneNo.text = displayPhoneNo

                    txtProfileCompany = findViewById(R.id.txtProfileCompany)
                    val displayCompany = "Company\n$companyPrint($companyInitialsPrint)"
                    txtProfileCompany.text = displayCompany

                    txtProfileSiteBranch = findViewById(R.id.txtProfileSiteBranch)
                    val displaySiteBranch = "Office/Site Branch\n$siteBranchPrint"
                    txtProfileSiteBranch.text = displaySiteBranch

                    txtProfileDepartment = findViewById(R.id.txtProfileDepartment)
                    val displayDepartment = "Department\n$departmentPrint"
                    txtProfileDepartment.text = displayDepartment

                    txtProfileJobTitle = findViewById(R.id.txtProfileJobTitle)
                    val displayJobTitle = "Job Title\n$jobTitlePrint"
                    txtProfileJobTitle.text = displayJobTitle
                }

            } while(cursor.moveToNext())
        }
        cursor.close()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.profile_option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemViewTimeAttendance -> {
                val intent = Intent(this,TimeAttendanceViewActivity::class.java)
                val passName = getName
                val passStartDate = "01-01-2000"
                val passEndDate = "31-12-2100"

                intent.putExtra("FullName",passName)
                intent.putExtra("StartDate",passStartDate)
                intent.putExtra("EndDate",passEndDate)
                startActivity(intent)
                return true
            }
            R.id.itemViewLeaveSchedule -> {
                val intent = Intent(this,LeaveScheduleView2Activity::class.java)
                val passName = getName
                val passStartDate = "01-01-2000"
                val passEndDate = "31-12-2100"

                intent.putExtra("FullName",passName)
                intent.putExtra("StartDate",passStartDate)
                intent.putExtra("EndDate",passEndDate)
                startActivity(intent)
                return true
            }
            R.id.subItemEditAccountTag -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_accounttagedit)

                val nameVerify = getName

                val account = resources.getStringArray(R.array.Account) //create variable to access listed items in string.xml
                val adapter = ArrayAdapter(this, R.layout.dropdown_item, account)

                val spinnerAccountTag = dialog.findViewById<AutoCompleteTextView>(R.id.spinnerAccountTag) //create variable to store spinner selection
                if (spinnerAccountTag != null) {
                    spinnerAccountTag.setAdapter(adapter)
                }

                val btnSaveAccountTagEdit = dialog.findViewById<Button>(R.id.btnSaveAccountTagEdit)
                if (btnSaveAccountTagEdit != null) {
                    btnSaveAccountTagEdit.setOnClickListener {

                        if (spinnerAccountTag != null) {
                            if(spinnerAccountTag.text.toString().isEmpty()){
                                Toast.makeText(this,"SELECT ACCOUNT TAG", Toast.LENGTH_SHORT).show()
                            } else {
                                val db = DBHelper(this, null)
                                val cursor = db.getLoginDetails()

                                if(cursor!!.moveToFirst()){
                                    do{
                                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                        val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                        if(nameVerify == namePrint.toString()){
                                            val id = idPrint.toLong()
                                            val saveAccountTag = spinnerAccountTag.text.toString()

                                            db.updateAccountTag(id, saveAccountTag)
                                            db.close()

                                            val intent = Intent(this,LoginActivity::class.java)
                                            startActivity(intent)

                                            Toast.makeText(this,"$nameVerify: CHANGED ACCOUNT TO $saveAccountTag", Toast.LENGTH_SHORT).show()
                                        }
                                    } while(cursor.moveToNext())
                                }
                                cursor.close()
                            }
                        }
                    }
                }
                val btnCancelAccountTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelAccountTagEdit)
                if (btnCancelAccountTagEdit != null) {
                    btnCancelAccountTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.subItemEditName -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_nameedit)

                val nameVerify = getName

                val editTxtFirstNameEdit = dialog.findViewById<EditText>(R.id.editTxtFirstNameEdit)
                val editTxtMiddleNameEdit = dialog.findViewById<EditText>(R.id.editTxtMiddleNameEdit)
                val editTxtLastNameEdit = dialog.findViewById<EditText>(R.id.editTxtLastNameEdit)

                val btnSaveNameTagEdit = dialog.findViewById<Button>(R.id.btnSaveNameEdit)
                if (btnSaveNameTagEdit != null) {
                    btnSaveNameTagEdit.setOnClickListener {
                        if (editTxtFirstNameEdit != null) {
                            if(editTxtFirstNameEdit.text.toString().trim().uppercase().isEmpty()){
                                Toast.makeText(this,"FIRST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                            } else if (editTxtLastNameEdit != null) {
                                if(editTxtLastNameEdit.text.toString().trim().uppercase().isEmpty()){
                                    Toast.makeText(this,"LAST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                } else {
                                    val db = DBHelper(this, null)
                                    val cursor = db.getLoginDetails()

                                    if(cursor!!.moveToFirst()){
                                        do{
                                            val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                            val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                            if(nameVerify == namePrint.toString()){
                                                val id = idPrint.toLong()
                                                val firstName = editTxtFirstNameEdit.text.toString().trim().uppercase()
                                                val middleName = editTxtMiddleNameEdit?.text.toString().trim().uppercase()
                                                val lastName = editTxtLastNameEdit.text.toString().trim().uppercase()

                                                if(middleName.isEmpty()){
                                                    val saveName = "$firstName $lastName"

                                                    db.updateName(id, saveName)
                                                    db.close()

                                                    Toast.makeText(this,"SAVED", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    val saveName = "$firstName $middleName $lastName"

                                                    db.updateName(id, saveName)
                                                    db.close()

                                                    val intent = Intent(this,LoginActivity::class.java)
                                                    startActivity(intent)

                                                    Toast.makeText(this,"$nameVerify: CHANGED NAME TO $saveName", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } while(cursor.moveToNext())
                                    }
                                    cursor.close()
                                }
                            }
                        }
                    }
                }
                val btnCancelNameTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelNameEdit)
                if (btnCancelNameTagEdit != null) {
                    btnCancelNameTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.subItemEditPersonalDetails -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_personaldetailsedit)

                val nameVerify = getName

                val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                val adapter = ArrayAdapter(this, R.layout.dropdown_item, genders)

                val spinnerGenderEdit = dialog.findViewById<AutoCompleteTextView>(R.id.spinnerGenderEdit) //create variable to store spinner selection
                if (spinnerGenderEdit != null) {
                    spinnerGenderEdit.setAdapter(adapter)
                }

                val editTxtNationalIDNoEdit = dialog.findViewById<EditText>(R.id.editTxtNationalIDNoEdit)
                val editTxtDateOfBirthEdit = dialog.findViewById<EditText>(R.id.editTxtDateOfBirthEdit)
                if (editTxtDateOfBirthEdit != null) {
                    editTxtDateOfBirthEdit.setOnClickListener {
                        val c = Calendar.getInstance()

                        val year1 = c.get(Calendar. YEAR)
                        val month = c.get(Calendar. MONTH)
                        val day = c.get(Calendar. DAY_OF_MONTH)

                        val datePickerDialog = DatePickerDialog(
                            this,
                            { _, year, monthOfYear, dayOfMonth ->
                                val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                                editTxtDateOfBirthEdit.setText(date)
                            },
                            year1,
                            month,
                            day
                        )
                        datePickerDialog.show()
                    }
                }

                val btnSavePersonalDetailsTagEdit = dialog.findViewById<Button>(R.id.btnSavePersonalDetailsEdit)
                if (btnSavePersonalDetailsTagEdit != null) {
                    btnSavePersonalDetailsTagEdit.setOnClickListener {
                        if (spinnerGenderEdit != null) {
                            if(spinnerGenderEdit.text.toString().isEmpty()){
                                Toast.makeText(this,"GENDER REQUIRED", Toast.LENGTH_SHORT).show()
                            } else if (editTxtNationalIDNoEdit != null) {
                                if(editTxtNationalIDNoEdit.text.toString().isEmpty()){
                                    Toast.makeText(this,"NATIONAL ID NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                } else if (editTxtDateOfBirthEdit != null) {
                                    if(editTxtDateOfBirthEdit.text.toString().isEmpty()){
                                        Toast.makeText(this,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else{
                                        val db = DBHelper(this, null)
                                        val cursor = db.getLoginDetails()

                                        if(cursor!!.moveToFirst()){
                                            do{
                                                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                if(nameVerify == namePrint.toString()){
                                                    val id = idPrint.toLong()
                                                    val saveGender = spinnerGenderEdit.text.toString()
                                                    val saveNationalId = editTxtNationalIDNoEdit.text.toString().trim().uppercase()
                                                    val saveDateOfBirth = editTxtDateOfBirthEdit.text.toString().trim()

                                                    db.updatePersonalDetails(id, saveGender, saveNationalId, saveDateOfBirth)
                                                    db.close()

                                                    Toast.makeText(this,"PERSONAL DETAILS: SAVED", Toast.LENGTH_SHORT).show()
                                                }
                                            } while(cursor.moveToNext())
                                        }
                                        cursor.close()
                                    }
                                }
                            }
                        }
                    }
                }
                val btnCancelPersonalDetailsTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelPersonalDetailsEdit)
                if (btnCancelPersonalDetailsTagEdit != null) {
                    btnCancelPersonalDetailsTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.subItemEditCompany -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_companyedit)

                val nameVerify = getName

                val editTxtJoinCompanyAdmissionKeyEdit = dialog.findViewById<EditText>(R.id.editTxtJoinCompanyAdmissionKeyEdit)
                val btnSaveCompanyTagEdit = dialog.findViewById<Button>(R.id.btnSaveCompanyEdit)
                if (btnSaveCompanyTagEdit != null) {
                    btnSaveCompanyTagEdit.setOnClickListener {
                        if (editTxtJoinCompanyAdmissionKeyEdit != null) {
                            if(editTxtJoinCompanyAdmissionKeyEdit.text.toString().trim().uppercase() == ""){
                                Toast.makeText(this,"COMPANY ADMISSION KEY REQUIRED", Toast.LENGTH_SHORT).show()
                            } else {
                                val companyAdmissionKeyEdit = editTxtJoinCompanyAdmissionKeyEdit.text.toString().trim().uppercase()

                                val db = DBHelper(this, null)
                                val cursor = db.getLoginDetails()

                                if(cursor!!.moveToFirst()){
                                    do{
                                        val companyNameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                                        val companyInitialsCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                                        val companyAdmissionKeyCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_ADM_KEY))

                                        if (companyAdmissionKeyCheck.toString() == companyAdmissionKeyEdit){
                                            val companyNameUser = companyNameCheck.toString()
                                            val companyInitialsUser = companyInitialsCheck.toString()

                                            if (cursor.moveToFirst()){
                                                do{
                                                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                    val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                    if(nameVerify == namePrint.toString()){
                                                        val id = idPrint.toLong()

                                                        db.updateCompany(id, companyNameUser, companyInitialsUser, companyAdmissionKeyEdit)
                                                        db.close()

                                                        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show()
                                                    }
                                                } while(cursor.moveToNext())
                                            }
                                            cursor.close()
                                        }
                                    } while (cursor.moveToNext())
                                }
                                cursor.close()
                            }
                        }
                    }
                }

                val btnCancelCompanyTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelCompanyEdit)
                if (btnCancelCompanyTagEdit != null) {
                    btnCancelCompanyTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.subItemEditJobDescription -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_jobdescriptionedit)

                val nameVerify = getName

                val editTxtOfficeBranchEdit = dialog.findViewById<EditText>(R.id.editTxtOfficeBranchEdit)
                val editTxtDepartmentEdit = dialog.findViewById<EditText>(R.id.editTxtDepartmentEdit)
                val editTxtJobTitleEdit = dialog.findViewById<EditText>(R.id.editTxtJobTitleEdit)

                val btnSaveJobDescriptionTagEdit = dialog.findViewById<Button>(R.id.btnSaveJobDescriptionEdit)
                if (btnSaveJobDescriptionTagEdit != null) {
                    btnSaveJobDescriptionTagEdit.setOnClickListener {
                        if (editTxtOfficeBranchEdit != null) {
                            if(editTxtOfficeBranchEdit.text.toString().trim().uppercase().isEmpty()){
                                Toast.makeText(this,"OFFICE/ SITE BRANCH REQUIRED", Toast.LENGTH_SHORT).show()
                            } else if (editTxtDepartmentEdit != null) {
                                if(editTxtDepartmentEdit.text.toString().trim().uppercase().isEmpty()){
                                    Toast.makeText(this,"DEPARTMENT REQUIRED", Toast.LENGTH_SHORT).show()
                                } else if (editTxtJobTitleEdit != null) {
                                    if(editTxtJobTitleEdit.text.toString().trim().uppercase().isEmpty()){
                                        Toast.makeText(this,"JOB TITLE REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else{
                                        val db = DBHelper(this, null)
                                        val cursor = db.getLoginDetails()

                                        if(cursor!!.moveToFirst()){
                                            do{
                                                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                if(nameVerify == namePrint.toString()){
                                                    val id = idPrint.toLong()
                                                    val saveOfficeSiteBranch = editTxtOfficeBranchEdit.text.toString().trim().uppercase()
                                                    val saveDepartment = editTxtDepartmentEdit.text.toString().trim().uppercase()
                                                    val saveJobTitle = editTxtJobTitleEdit.text.toString().trim().uppercase()


                                                    db.updateJobDescription(id, saveOfficeSiteBranch, saveDepartment, saveJobTitle)
                                                    db.close()

                                                    Toast.makeText(this,"SAVED", Toast.LENGTH_SHORT).show()
                                                }
                                            } while(cursor.moveToNext())
                                        }
                                        cursor.close()
                                    }
                                }
                            }
                        }
                    }
                }
                val btnCancelJobDescriptionTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelJobDescriptionEdit)
                if (btnCancelJobDescriptionTagEdit != null) {
                    btnCancelJobDescriptionTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.subItemEditContactInformation -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_contactinformationedit)

                val nameVerify = getName

                val editTxtEmailAddressEdit = dialog.findViewById<EditText>(R.id.editTxtEmailAddressEdit)
                val editTxtTelephoneNumberEdit = dialog.findViewById<EditText>(R.id.editTxtTelephoneNumberEdit)

                val btnSaveContactInformationTagEdit = dialog.findViewById<Button>(R.id.btnSaveContactInformationEdit)
                if (btnSaveContactInformationTagEdit != null) {
                    btnSaveContactInformationTagEdit.setOnClickListener {
                        if (editTxtEmailAddressEdit != null) {
                            if(editTxtEmailAddressEdit.text.toString().trim().uppercase().isEmpty()){
                                Toast.makeText(this,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                            } else if (editTxtTelephoneNumberEdit != null) {
                                if(editTxtTelephoneNumberEdit.text.toString().trim().uppercase().isEmpty()){
                                    Toast.makeText(this,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                } else{
                                    val db = DBHelper(this, null)
                                    val cursor = db.getLoginDetails()

                                    if(cursor!!.moveToFirst()){
                                        do{
                                            val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                            val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                            if(nameVerify == namePrint.toString()){
                                                val id = idPrint.toLong()
                                                val saveEmailAddress = editTxtEmailAddressEdit.text.toString().trim()
                                                val saveTelephoneNumber = editTxtTelephoneNumberEdit.text.toString().trim()


                                                db.updateContactInformation(id, saveEmailAddress, saveTelephoneNumber)
                                                db.close()

                                                Toast.makeText(this,"SAVED", Toast.LENGTH_SHORT).show()
                                            }
                                        } while(cursor.moveToNext())
                                    }
                                    cursor.close()
                                }
                            }
                        }
                    }
                }

                val btnCancelContactInformationTagEdit = dialog.findViewById<ImageView>(R.id.btnCancelContactInformationEdit)
                if (btnCancelContactInformationTagEdit != null) {
                    btnCancelContactInformationTagEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.itemViewChangePIN -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_securityedit)

                val nameVerify = getName

                val editTxtOldPINEdit = dialog.findViewById<EditText>(R.id.editTxtOldPINEdit)
                val editTxtNewPINEdit = dialog.findViewById<EditText>(R.id.editTxtNewPINEdit)
                val editTxtConfirmNewPINEdit = dialog.findViewById<EditText>(R.id.editTxtConfirmNewPINEdit)

                val btnSavePINEdit = dialog.findViewById<Button>(R.id.btnSavePINEdit)
                if (btnSavePINEdit != null) {
                    btnSavePINEdit.setOnClickListener {
                        val db = DBHelper(this, null)
                        val cursor = db.getLoginDetails()

                        if(cursor!!.moveToFirst()){
                            do{
                                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                val pinPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))

                                if(nameVerify == namePrint.toString()){
                                    val oldPin = pinPrint.toString()

                                    if (editTxtOldPINEdit != null) {
                                        if(editTxtOldPINEdit.text.toString().trim() != oldPin){
                                            Toast.makeText(this,"INCORRECT CURRENT PIN", Toast.LENGTH_SHORT).show()
                                        } else if (editTxtNewPINEdit != null) {
                                            if(editTxtNewPINEdit.text.toString().trim().length != 4){
                                                Toast.makeText(this,"4-DIGIT PIN REQUIRED", Toast.LENGTH_SHORT).show()
                                            } else if (editTxtConfirmNewPINEdit != null) {
                                                if(editTxtNewPINEdit.text.toString().trim() != editTxtConfirmNewPINEdit.text.toString().trim()){
                                                    Toast.makeText(this,"MATCHING PINS REQUIRED", Toast.LENGTH_SHORT).show()
                                                } else if(oldPin == editTxtOldPINEdit.text.toString().trim()){
                                                    if(cursor.moveToFirst()){
                                                        do{
                                                            val namePrint1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                            val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                                            if(nameVerify == namePrint1.toString() && editTxtNewPINEdit.text.toString().trim() == editTxtConfirmNewPINEdit.text.toString().trim()){
                                                                val id = idPrint.toLong()

                                                                val savePIN = editTxtConfirmNewPINEdit.text.toString().trim()

                                                                db.updatePIN(id, savePIN)
                                                                db.close()

                                                                val intent = Intent(this,LoginActivity::class.java)
                                                                startActivity(intent)

                                                                Toast.makeText(this,"$nameVerify: CHANGED PIN", Toast.LENGTH_SHORT).show()
                                                            }
                                                        } while(cursor.moveToNext())
                                                    }
                                                    cursor.close()
                                                }
                                            }
                                        }
                                    }
                                }
                            } while(cursor.moveToNext())
                        }
                        cursor.close()
                    }
                }

                val btnCancelPINEdit = dialog.findViewById<ImageView>(R.id.btnCancelPINEdit)
                if (btnCancelPINEdit != null) {
                    btnCancelPINEdit.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
                return true
            }
            R.id.itemViewDeleteProfile -> {
                val dialog = MaterialAlertDialogBuilder(this,R.style.RoundedMaterialDialog).setView(R.layout.dialog_deleteprofile).show()

                val nameVerify = getName

                val editTxtLeaveAdminKeyDelete = dialog.findViewById<EditText>(R.id.editTxtLeaveAdminKeyDelete)

                val btnYesDelete = dialog.findViewById<Button>(R.id.btnYesDelete)
                if (btnYesDelete != null) {
                    btnYesDelete.setOnClickListener {
                        val adminKeyDelete = "h4ck-th3-pl4n3t"
                        if (editTxtLeaveAdminKeyDelete != null) {
                            if(editTxtLeaveAdminKeyDelete.text.toString().trim() != adminKeyDelete){
                                Toast.makeText(this,"ENTER VALID ADMIN KEY", Toast.LENGTH_SHORT).show()
                            } else if(editTxtLeaveAdminKeyDelete.text.toString().trim() == adminKeyDelete){
                                val db = DBHelper(this, null)
                                val cursor = db.getLoginDetails()

                                if(cursor!!.moveToFirst()){
                                    do{
                                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                                        if(nameVerify == namePrint.toString()){
                                            val name = namePrint.toString()

                                            db.deleteProfile(name)
                                            db.close()

                                            val intent = Intent(this,LoginActivity::class.java)
                                            startActivity(intent)
                                            Toast.makeText(this,"$name;\nSUCCESSFULLY DELETED ", Toast.LENGTH_SHORT).show()
                                        }
                                    } while(cursor.moveToNext())
                                }
                                cursor.close()
                            }
                        }
                    }
                }

                val btnNoDelete = dialog.findViewById<Button>(R.id.btnNoDelete)
                if (btnNoDelete != null) {
                    btnNoDelete.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}