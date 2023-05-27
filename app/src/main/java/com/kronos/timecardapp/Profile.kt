package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class Profile : Fragment() {
    private lateinit var employeeList: ArrayList<String>
    private lateinit var spinnerProfileEmployeeName: AutoCompleteTextView
    private lateinit var addProfileBtn: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

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

        spinnerProfileEmployeeName = v.findViewById(R.id.spinnerProfileEmployeeName)
        spinnerProfileEmployeeName.setAdapter(adapter)

        val btnGenerateProfiles = v.findViewById<Button>(R.id.btnGenerateProfiles)
        btnGenerateProfiles.setOnClickListener {
            val intent = Intent(v.context,ProfileViewListActivity::class.java)

            val passName = spinnerProfileEmployeeName.text.toString()

            intent.putExtra("NameCheck",passName)

            spinnerProfileEmployeeName.text.clear()
            startActivity(intent)
        }
        addProfileBtn = v.findViewById(R.id.addProfileBtn)
        addProfileBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(v.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_signupaccounttag).show()

            var displayAccountTag: String

            val editTxtAdminKey = dialog.findViewById<EditText>(R.id.editTxtAdminKey)

            val btnCancelAccountTag = dialog.findViewById<ImageView>(R.id.btnCancelAccountTag)
            if (btnCancelAccountTag != null) {
                btnCancelAccountTag.setOnClickListener{
                    dialog.dismiss()
                }
            }

            val btnAdminSignUp = dialog.findViewById<LinearLayout>(R.id.btnAdminSignUp)
            if (btnAdminSignUp != null) {
                btnAdminSignUp.setOnClickListener {
                    val adminKeyHolder = "K1LL-81LL-V0L12"

                    if (editTxtAdminKey != null) {
                        if(editTxtAdminKey.text.toString().trim().uppercase() == adminKeyHolder){
                            displayAccountTag = "ADMINISTRATOR"

                            val dialog1 = BottomSheetDialog(v.context)
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog1.setContentView(R.layout.bottomsheet_signupname)

                            val txtAccountTypeSignUp = dialog1.findViewById<TextView>(R.id.txtAccountTypeSignUp)
                            if (txtAccountTypeSignUp != null) {
                                txtAccountTypeSignUp.text = displayAccountTag
                            }
                            val accountTag1 = txtAccountTypeSignUp?.text.toString()

                            val editTxtFirstNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtFirstNameSignUp)
                            val editTxtMiddleNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtMiddleNameSignUp)
                            val editTxtLastNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtLastNameSignUp)

                            val btnCancelName = dialog1.findViewById<ImageView>(R.id.btnCancelName)
                            if (btnCancelName != null) {
                                btnCancelName.setOnClickListener{
                                    dialog1.dismiss()
                                }
                            }

                            val btnNextSignUpPersonalDetails = dialog1.findViewById<Button>(R.id.btnNextSignUpPersonalDetails)
                            if (btnNextSignUpPersonalDetails != null) {
                                btnNextSignUpPersonalDetails.setOnClickListener{
                                    if (editTxtFirstNameSignUp != null) {
                                        if(editTxtFirstNameSignUp.text.toString().trim().isEmpty()){
                                            Toast.makeText(v.context, "FIRST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                        } else if (editTxtLastNameSignUp != null) {
                                            if(editTxtLastNameSignUp.text.toString().trim().isEmpty()){
                                                Toast.makeText(v.context, "LAST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                            } else if (editTxtMiddleNameSignUp != null) {
                                                if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtMiddleNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()) {
                                                    val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtMiddleNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                                    val dialog2 = BottomSheetDialog(v.context)
                                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                    dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                                    val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                                    val adapter1 = ArrayAdapter(v.context, R.layout.dropdown_item, genders)

                                                    val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                                    if (spinnerGenderSignUp != null) {
                                                        spinnerGenderSignUp.setAdapter(adapter1)
                                                    }

                                                    val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                                    val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                                    if (editTxtDateOfBirthSignUp != null) {
                                                        editTxtDateOfBirthSignUp.setOnClickListener {
                                                            val c = Calendar.getInstance()

                                                            val year1 = c.get(Calendar. YEAR)
                                                            val month = c.get(Calendar. MONTH)
                                                            val day = c.get(Calendar. DAY_OF_MONTH)

                                                            val datePickerDialog = DatePickerDialog(
                                                                v.context,
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
                                                    }

                                                    val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                                    if (btnCancelPersonalDetails != null) {
                                                        btnCancelPersonalDetails.setOnClickListener{
                                                            dialog2.dismiss()
                                                        }
                                                    }

                                                    val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                                    if (btnNextSignUpJobDescription != null) {
                                                        btnNextSignUpJobDescription.setOnClickListener {
                                                            if (spinnerGenderSignUp != null) {
                                                                if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                                    Toast.makeText(v.context,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                                } else if (editTxtNationalIDNo != null) {
                                                                    if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                        Toast.makeText(v.context,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                                    } else if (editTxtDateOfBirthSignUp != null) {
                                                                        if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                            Toast.makeText(v.context,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                        } else{
                                                                            val passGender = spinnerGenderSignUp.text.toString()
                                                                            val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                            val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                            val dialog3 = BottomSheetDialog(v.context)
                                                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                            dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                            val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                            val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                            val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                            val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                            if (btnCancelJobDescription != null) {
                                                                                btnCancelJobDescription.setOnClickListener{
                                                                                    dialog3.dismiss()
                                                                                }
                                                                            }

                                                                            val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                            if (btnNextSignUpContactInformation != null) {
                                                                                btnNextSignUpContactInformation.setOnClickListener {
                                                                                    if (editTxtOfficeBranchSignUp != null) {
                                                                                        if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(v.context,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else if (editTxtDepartmentSignUp != null) {
                                                                                            if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                                Toast.makeText(v.context,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                            } else if (editTxtJobTitleSignUp != null) {
                                                                                                if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                                    Toast.makeText(v.context,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                } else {
                                                                                                    val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                                    val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                                    val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                                    val dialog4 = BottomSheetDialog(v.context)
                                                                                                    dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                    dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                                    val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                                    val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                                    val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                                    if (btnCancelContactInformation != null) {
                                                                                                        btnCancelContactInformation.setOnClickListener{
                                                                                                            dialog4.dismiss()
                                                                                                        }
                                                                                                    }

                                                                                                    val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                                    if (btnNextSignUpSetPIN != null) {
                                                                                                        btnNextSignUpSetPIN.setOnClickListener {
                                                                                                            if (editTxtTelephoneNumberSignUp != null) {
                                                                                                                if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                                    Toast.makeText(v.context,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                } else if (editTxtEmailAddressSignUp != null) {
                                                                                                                    if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                        Toast.makeText(v.context,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                    } else{
                                                                                                                        val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                        val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                        val dialog5 = BottomSheetDialog(v.context)
                                                                                                                        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                                                                                        dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                        val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                        val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                        val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                        if (btnCancelSetPin != null) {
                                                                                                                            btnCancelSetPin.setOnClickListener{
                                                                                                                                dialog5.dismiss()
                                                                                                                            }
                                                                                                                        }

                                                                                                                        val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                        if (btnFinishSignUp != null) {
                                                                                                                            btnFinishSignUp.setOnClickListener {
                                                                                                                                if (editTxtPINSignUp != null) {
                                                                                                                                    if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                        Toast.makeText(v.context,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                    } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                        if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                            Toast.makeText(v.context,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                        } else{
                                                                                                                                            val emailAddress = passEmailAddress
                                                                                                                                            val telephoneNumber = passTelephoneNumber
                                                                                                                                            val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                            val department = passDepartment
                                                                                                                                            val jobTitle = passJobTitle
                                                                                                                                            val gender = passGender
                                                                                                                                            val nationalId = passNationalId
                                                                                                                                            val dateOfBirth = passDateOfBirth
                                                                                                                                            val name = passName
                                                                                                                                            val accountTag = accountTag1
                                                                                                                                            val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                            val company = "to be set"
                                                                                                                                            val companyInitials = "to be set"
                                                                                                                                            val companyAdmissionKey = "to be set"

                                                                                                                                            val db = DBHelper(v.context, null)
                                                                                                                                            db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                            dialog.dismiss()
                                                                                                                                            dialog1.dismiss()
                                                                                                                                            dialog2.dismiss()
                                                                                                                                            dialog3.dismiss()
                                                                                                                                            dialog4.dismiss()
                                                                                                                                            dialog5.dismiss()

                                                                                                                                            val dialog6 = MaterialAlertDialogBuilder(v.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmaccount).show()

                                                                                                                                            val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                            val displayName = "Name\n$name"
                                                                                                                                            if (txtName != null) {
                                                                                                                                                txtName.text = displayName
                                                                                                                                            }

                                                                                                                                            val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                            val displayGender = "Gender\n$gender"
                                                                                                                                            if (txtGender != null) {
                                                                                                                                                txtGender.text = displayGender
                                                                                                                                            }

                                                                                                                                            val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                            val displayNationalId = "National ID No.\n$nationalId"
                                                                                                                                            if (txtNationalId != null) {
                                                                                                                                                txtNationalId.text = displayNationalId
                                                                                                                                            }

                                                                                                                                            val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                            val displayDateOfBirth = "Date of Birth\n$dateOfBirth"
                                                                                                                                            if (txtDateOfBirth != null) {
                                                                                                                                                txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                            }

                                                                                                                                            val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                            val displaySiteBranch = "Office/Site Branch\n$officeSiteBranch"
                                                                                                                                            if (txtSiteBranch != null) {
                                                                                                                                                txtSiteBranch.text = displaySiteBranch
                                                                                                                                            }

                                                                                                                                            val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                            val displayDepartment = "Department\n$department"
                                                                                                                                            if (txtDepartment != null) {
                                                                                                                                                txtDepartment.text = displayDepartment
                                                                                                                                            }

                                                                                                                                            val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                            val displayJobTitle = "Job Title\n$jobTitle"
                                                                                                                                            if (txtJobTitle != null) {
                                                                                                                                                txtJobTitle.text = displayJobTitle
                                                                                                                                            }

                                                                                                                                            val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                            val displayEmail = "E-Mail Address\n$emailAddress"
                                                                                                                                            if (txtEmail != null) {
                                                                                                                                                txtEmail.text = displayEmail
                                                                                                                                            }

                                                                                                                                            val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                            val displayTelephone = "Telephone No.\n$telephoneNumber"
                                                                                                                                            if (txtTelephone != null) {
                                                                                                                                                txtTelephone.text = displayTelephone
                                                                                                                                            }

                                                                                                                                            val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                            if (btnOkAttendanceConfirm != null) {
                                                                                                                                                btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                                    dialog6.dismiss()
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        dialog5.show()
                                                                                                                        dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                        dialog5.window!!.setBackgroundDrawable(
                                                                                                                            ColorDrawable(
                                                                                                                                Color.TRANSPARENT)
                                                                                                                        )
                                                                                                                        dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                        dialog5.window!!.setGravity(
                                                                                                                            Gravity.BOTTOM)

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    dialog4.show()
                                                                                                    dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                    dialog4.window!!.setBackgroundDrawable(
                                                                                                        ColorDrawable(
                                                                                                            Color.TRANSPARENT)
                                                                                                    )
                                                                                                    dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                    dialog4.window!!.setGravity(
                                                                                                        Gravity.BOTTOM)

                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            dialog3.show()
                                                                            dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                            dialog3.window!!.setBackgroundDrawable(
                                                                                ColorDrawable(Color.TRANSPARENT)
                                                                            )
                                                                            dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                            dialog3.window!!.setGravity(
                                                                                Gravity.BOTTOM)
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    dialog2.show()
                                                    dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                    dialog2.window!!.setBackgroundDrawable(
                                                        ColorDrawable(Color.TRANSPARENT)
                                                    )
                                                    dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                    dialog2.window!!.setGravity(Gravity.BOTTOM)

                                                } else if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()){
                                                    val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                                    val dialog2 = BottomSheetDialog(v.context)
                                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                    dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                                    val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                                    val adapter1 = ArrayAdapter(v.context, R.layout.dropdown_item, genders)

                                                    val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                                    if (spinnerGenderSignUp != null) {
                                                        spinnerGenderSignUp.setAdapter(adapter1)
                                                    }

                                                    val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                                    val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                                    if (editTxtDateOfBirthSignUp != null) {
                                                        editTxtDateOfBirthSignUp.setOnClickListener {
                                                            val c = Calendar.getInstance()

                                                            val year1 = c.get(Calendar. YEAR)
                                                            val month = c.get(Calendar. MONTH)
                                                            val day = c.get(Calendar. DAY_OF_MONTH)

                                                            val datePickerDialog = DatePickerDialog(
                                                                v.context,
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
                                                    }

                                                    val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                                    if (btnCancelPersonalDetails != null) {
                                                        btnCancelPersonalDetails.setOnClickListener{
                                                            dialog2.dismiss()
                                                        }
                                                    }

                                                    val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                                    if (btnNextSignUpJobDescription != null) {
                                                        btnNextSignUpJobDescription.setOnClickListener {
                                                            if (spinnerGenderSignUp != null) {
                                                                if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                                    Toast.makeText(v.context,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                                } else if (editTxtNationalIDNo != null) {
                                                                    if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                        Toast.makeText(v.context,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                                    } else if (editTxtDateOfBirthSignUp != null) {
                                                                        if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                            Toast.makeText(v.context,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                        } else{
                                                                            val passGender = spinnerGenderSignUp.text.toString()
                                                                            val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                            val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                            val dialog3 = BottomSheetDialog(v.context)
                                                                            dialog3.requestWindowFeature(
                                                                                Window.FEATURE_NO_TITLE)
                                                                            dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                            val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                            val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                            val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                            val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                            if (btnCancelJobDescription != null) {
                                                                                btnCancelJobDescription.setOnClickListener{
                                                                                    dialog3.dismiss()
                                                                                }
                                                                            }

                                                                            val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                            if (btnNextSignUpContactInformation != null) {
                                                                                btnNextSignUpContactInformation.setOnClickListener {
                                                                                    if (editTxtOfficeBranchSignUp != null) {
                                                                                        if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(v.context,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else if (editTxtDepartmentSignUp != null) {
                                                                                            if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                                Toast.makeText(v.context,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                            } else if (editTxtJobTitleSignUp != null) {
                                                                                                if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                                    Toast.makeText(v.context,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                } else {
                                                                                                    val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                                    val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                                    val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                                    val dialog4 = BottomSheetDialog(v.context)
                                                                                                    dialog4.requestWindowFeature(
                                                                                                        Window.FEATURE_NO_TITLE)
                                                                                                    dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                                    val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                                    val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                                    val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                                    if (btnCancelContactInformation != null) {
                                                                                                        btnCancelContactInformation.setOnClickListener{
                                                                                                            dialog4.dismiss()
                                                                                                        }
                                                                                                    }

                                                                                                    val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                                    if (btnNextSignUpSetPIN != null) {
                                                                                                        btnNextSignUpSetPIN.setOnClickListener {
                                                                                                            if (editTxtTelephoneNumberSignUp != null) {
                                                                                                                if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                                    Toast.makeText(v.context,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                } else if (editTxtEmailAddressSignUp != null) {
                                                                                                                    if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                        Toast.makeText(v.context,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                                    } else{
                                                                                                                        val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                        val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                        val dialog5 = BottomSheetDialog(v.context)
                                                                                                                        dialog5.requestWindowFeature(
                                                                                                                            Window.FEATURE_NO_TITLE)
                                                                                                                        dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                        val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                        val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                        val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                        if (btnCancelSetPin != null) {
                                                                                                                            btnCancelSetPin.setOnClickListener{
                                                                                                                                dialog5.dismiss()
                                                                                                                            }
                                                                                                                        }

                                                                                                                        val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                        if (btnFinishSignUp != null) {
                                                                                                                            btnFinishSignUp.setOnClickListener {
                                                                                                                                if (editTxtPINSignUp != null) {
                                                                                                                                    if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                        Toast.makeText(v.context,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                    } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                        if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                            Toast.makeText(v.context,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                        } else{
                                                                                                                                            val emailAddress = passEmailAddress
                                                                                                                                            val telephoneNumber = passTelephoneNumber
                                                                                                                                            val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                            val department = passDepartment
                                                                                                                                            val jobTitle = passJobTitle
                                                                                                                                            val gender = passGender
                                                                                                                                            val nationalId = passNationalId
                                                                                                                                            val dateOfBirth = passDateOfBirth
                                                                                                                                            val name = passName
                                                                                                                                            val accountTag = accountTag1
                                                                                                                                            val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                            val company = "to be set"
                                                                                                                                            val companyInitials = "to be set"
                                                                                                                                            val companyAdmissionKey = "to be set"

                                                                                                                                            val db = DBHelper(v.context, null)
                                                                                                                                            db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                            dialog.dismiss()
                                                                                                                                            dialog1.dismiss()
                                                                                                                                            dialog2.dismiss()
                                                                                                                                            dialog3.dismiss()
                                                                                                                                            dialog4.dismiss()
                                                                                                                                            dialog5.dismiss()

                                                                                                                                            val dialog6 = MaterialAlertDialogBuilder(v.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmaccount).show()

                                                                                                                                            val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                            val displayName = "Name\n$name"
                                                                                                                                            if (txtName != null) {
                                                                                                                                                txtName.text = displayName
                                                                                                                                            }

                                                                                                                                            val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                            val displayGender = "Gender\n$gender"
                                                                                                                                            if (txtGender != null) {
                                                                                                                                                txtGender.text = displayGender
                                                                                                                                            }

                                                                                                                                            val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                            val displayNationalId = "National ID No.\n$nationalId"
                                                                                                                                            if (txtNationalId != null) {
                                                                                                                                                txtNationalId.text = displayNationalId
                                                                                                                                            }

                                                                                                                                            val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                            val displayDateOfBirth = "Date of Birth\n$dateOfBirth"
                                                                                                                                            if (txtDateOfBirth != null) {
                                                                                                                                                txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                            }

                                                                                                                                            val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                            val displaySiteBranch = "Office/Site Branch\n$officeSiteBranch"
                                                                                                                                            if (txtSiteBranch != null) {
                                                                                                                                                txtSiteBranch.text = displaySiteBranch
                                                                                                                                            }

                                                                                                                                            val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                            val displayDepartment = "Department\n$department"
                                                                                                                                            if (txtDepartment != null) {
                                                                                                                                                txtDepartment.text = displayDepartment
                                                                                                                                            }

                                                                                                                                            val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                            val displayJobTitle = "Job Title\n$jobTitle"
                                                                                                                                            if (txtJobTitle != null) {
                                                                                                                                                txtJobTitle.text = displayJobTitle
                                                                                                                                            }

                                                                                                                                            val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                            val displayEmail = "E-Mail Address\n$emailAddress"
                                                                                                                                            if (txtEmail != null) {
                                                                                                                                                txtEmail.text = displayEmail
                                                                                                                                            }

                                                                                                                                            val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                            val displayTelephone = "Telephone No.\n$telephoneNumber"
                                                                                                                                            if (txtTelephone != null) {
                                                                                                                                                txtTelephone.text = displayTelephone
                                                                                                                                            }

                                                                                                                                            val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                            if (btnOkAttendanceConfirm != null) {
                                                                                                                                                btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                                    dialog6.dismiss()
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        dialog5.show()
                                                                                                                        dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                        dialog5.window!!.setBackgroundDrawable(
                                                                                                                            ColorDrawable(
                                                                                                                                Color.TRANSPARENT)
                                                                                                                        )
                                                                                                                        dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                        dialog5.window!!.setGravity(
                                                                                                                            Gravity.BOTTOM)

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    dialog4.show()
                                                                                                    dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                    dialog4.window!!.setBackgroundDrawable(
                                                                                                        ColorDrawable(
                                                                                                            Color.TRANSPARENT)
                                                                                                    )
                                                                                                    dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                    dialog4.window!!.setGravity(
                                                                                                        Gravity.BOTTOM)

                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            dialog3.show()
                                                                            dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                            dialog3.window!!.setBackgroundDrawable(
                                                                                ColorDrawable(Color.TRANSPARENT)
                                                                            )
                                                                            dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                            dialog3.window!!.setGravity(
                                                                                Gravity.BOTTOM)


                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    dialog2.show()
                                                    dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                    dialog2.window!!.setBackgroundDrawable(
                                                        ColorDrawable(Color.TRANSPARENT)
                                                    )
                                                    dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                    dialog2.window!!.setGravity(Gravity.BOTTOM)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            dialog1.show()
                            dialog1.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog1.window!!.attributes.windowAnimations = R.style.DialogAnimation
                            dialog1.window!!.setGravity(Gravity.BOTTOM)

                        } else if (editTxtAdminKey.text.toString().isEmpty()){
                            Toast.makeText(v.context,"INSERT ADMIN KEY", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(v.context,"INSERT VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            val btnUserSignUp = dialog.findViewById<LinearLayout>(R.id.btnUserSignUp)
            if (btnUserSignUp != null) {
                btnUserSignUp.setOnClickListener {
                    displayAccountTag = "USER"

                    val dialog1 = BottomSheetDialog(v.context)
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog1.setContentView(R.layout.bottomsheet_signupname)

                    val txtAccountTypeSignUp = dialog1.findViewById<TextView>(R.id.txtAccountTypeSignUp)
                    if (txtAccountTypeSignUp != null) {
                        txtAccountTypeSignUp.text = displayAccountTag
                    }
                    val accountTag1 = txtAccountTypeSignUp?.text.toString()

                    val editTxtFirstNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtFirstNameSignUp)
                    val editTxtMiddleNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtMiddleNameSignUp)
                    val editTxtLastNameSignUp = dialog1.findViewById<EditText>(R.id.editTxtLastNameSignUp)

                    val btnCancelName = dialog1.findViewById<ImageView>(R.id.btnCancelName)
                    if (btnCancelName != null) {
                        btnCancelName.setOnClickListener{
                            dialog1.dismiss()
                        }
                    }

                    val btnNextSignUpPersonalDetails = dialog1.findViewById<Button>(R.id.btnNextSignUpPersonalDetails)
                    if (btnNextSignUpPersonalDetails != null) {
                        btnNextSignUpPersonalDetails.setOnClickListener{
                            if (editTxtFirstNameSignUp != null) {
                                if(editTxtFirstNameSignUp.text.toString().trim().isEmpty()){
                                    Toast.makeText(v.context, "FIRST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                } else if (editTxtLastNameSignUp != null) {
                                    if(editTxtLastNameSignUp.text.toString().trim().isEmpty()){
                                        Toast.makeText(v.context, "LAST NAME REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else if (editTxtMiddleNameSignUp != null) {
                                        if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtMiddleNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()) {
                                            val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtMiddleNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                            val dialog2 = BottomSheetDialog(v.context)
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                            val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                            val adapter1 = ArrayAdapter(v.context, R.layout.dropdown_item, genders)

                                            val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                            if (spinnerGenderSignUp != null) {
                                                spinnerGenderSignUp.setAdapter(adapter1)
                                            }

                                            val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                            val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                            if (editTxtDateOfBirthSignUp != null) {
                                                editTxtDateOfBirthSignUp.setOnClickListener {
                                                    val c = Calendar.getInstance()

                                                    val year1 = c.get(Calendar. YEAR)
                                                    val month = c.get(Calendar. MONTH)
                                                    val day = c.get(Calendar. DAY_OF_MONTH)

                                                    val datePickerDialog = DatePickerDialog(
                                                        v.context,
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
                                            }

                                            val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                            if (btnCancelPersonalDetails != null) {
                                                btnCancelPersonalDetails.setOnClickListener{
                                                    dialog2.dismiss()
                                                }
                                            }

                                            val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                            if (btnNextSignUpJobDescription != null) {
                                                btnNextSignUpJobDescription.setOnClickListener {
                                                    if (spinnerGenderSignUp != null) {
                                                        if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                            Toast.makeText(v.context,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                        } else if (editTxtNationalIDNo != null) {
                                                            if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                Toast.makeText(v.context,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                            } else if (editTxtDateOfBirthSignUp != null) {
                                                                if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                    Toast.makeText(v.context,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                } else{
                                                                    val passGender = spinnerGenderSignUp.text.toString()
                                                                    val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                    val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                    val dialog3 = BottomSheetDialog(v.context)
                                                                    dialog3.requestWindowFeature(
                                                                        Window.FEATURE_NO_TITLE)
                                                                    dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                    val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                    val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                    val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                    val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                    if (btnCancelJobDescription != null) {
                                                                        btnCancelJobDescription.setOnClickListener{
                                                                            dialog3.dismiss()
                                                                        }
                                                                    }

                                                                    val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                    if (btnNextSignUpContactInformation != null) {
                                                                        btnNextSignUpContactInformation.setOnClickListener {
                                                                            if (editTxtOfficeBranchSignUp != null) {
                                                                                if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                    Toast.makeText(v.context,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                } else if (editTxtDepartmentSignUp != null) {
                                                                                    if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                        Toast.makeText(v.context,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                    } else if (editTxtJobTitleSignUp != null) {
                                                                                        if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(v.context,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else {
                                                                                            val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                            val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                            val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                            val dialog4 = BottomSheetDialog(v.context)
                                                                                            dialog4.requestWindowFeature(
                                                                                                Window.FEATURE_NO_TITLE)
                                                                                            dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                            val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                            val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                            val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                            if (btnCancelContactInformation != null) {
                                                                                                btnCancelContactInformation.setOnClickListener{
                                                                                                    dialog4.dismiss()
                                                                                                }
                                                                                            }

                                                                                            val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                            if (btnNextSignUpSetPIN != null) {
                                                                                                btnNextSignUpSetPIN.setOnClickListener {
                                                                                                    if (editTxtTelephoneNumberSignUp != null) {
                                                                                                        if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                            Toast.makeText(v.context,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                        } else if (editTxtEmailAddressSignUp != null) {
                                                                                                            if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                Toast.makeText(v.context,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                            } else{
                                                                                                                val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                val dialog5 = BottomSheetDialog(v.context)
                                                                                                                dialog5.requestWindowFeature(
                                                                                                                    Window.FEATURE_NO_TITLE)
                                                                                                                dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                if (btnCancelSetPin != null) {
                                                                                                                    btnCancelSetPin.setOnClickListener{
                                                                                                                        dialog5.dismiss()
                                                                                                                    }
                                                                                                                }

                                                                                                                val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                if (btnFinishSignUp != null) {
                                                                                                                    btnFinishSignUp.setOnClickListener {
                                                                                                                        if (editTxtPINSignUp != null) {
                                                                                                                            if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                Toast.makeText(v.context,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                            } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                    Toast.makeText(v.context,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                } else{
                                                                                                                                    val emailAddress = passEmailAddress
                                                                                                                                    val telephoneNumber = passTelephoneNumber
                                                                                                                                    val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                    val department = passDepartment
                                                                                                                                    val jobTitle = passJobTitle
                                                                                                                                    val gender = passGender
                                                                                                                                    val nationalId = passNationalId
                                                                                                                                    val dateOfBirth = passDateOfBirth
                                                                                                                                    val name = passName
                                                                                                                                    val accountTag = accountTag1
                                                                                                                                    val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                    val company = "to be set"
                                                                                                                                    val companyInitials = "to be set"
                                                                                                                                    val companyAdmissionKey = "to be set"

                                                                                                                                    val db = DBHelper(v.context, null)
                                                                                                                                    db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                    dialog.dismiss()
                                                                                                                                    dialog1.dismiss()
                                                                                                                                    dialog2.dismiss()
                                                                                                                                    dialog3.dismiss()
                                                                                                                                    dialog4.dismiss()
                                                                                                                                    dialog5.dismiss()

                                                                                                                                    val dialog6 = MaterialAlertDialogBuilder(v.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmaccount).show()

                                                                                                                                    val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                    val displayName = "Name\n$name"
                                                                                                                                    if (txtName != null) {
                                                                                                                                        txtName.text = displayName
                                                                                                                                    }

                                                                                                                                    val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                    val displayGender = "Gender\n$gender"
                                                                                                                                    if (txtGender != null) {
                                                                                                                                        txtGender.text = displayGender
                                                                                                                                    }

                                                                                                                                    val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                    val displayNationalId = "National ID No.\n$nationalId"
                                                                                                                                    if (txtNationalId != null) {
                                                                                                                                        txtNationalId.text = displayNationalId
                                                                                                                                    }

                                                                                                                                    val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                    val displayDateOfBirth = "Date of Birth\n$dateOfBirth"
                                                                                                                                    if (txtDateOfBirth != null) {
                                                                                                                                        txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                    }

                                                                                                                                    val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                    val displaySiteBranch = "Office/Site Branch\n$officeSiteBranch"
                                                                                                                                    if (txtSiteBranch != null) {
                                                                                                                                        txtSiteBranch.text = displaySiteBranch
                                                                                                                                    }

                                                                                                                                    val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                    val displayDepartment = "Department\n$department"
                                                                                                                                    if (txtDepartment != null) {
                                                                                                                                        txtDepartment.text = displayDepartment
                                                                                                                                    }

                                                                                                                                    val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                    val displayJobTitle = "Job Title\n$jobTitle"
                                                                                                                                    if (txtJobTitle != null) {
                                                                                                                                        txtJobTitle.text = displayJobTitle
                                                                                                                                    }

                                                                                                                                    val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                    val displayEmail = "E-Mail Address\n$emailAddress"
                                                                                                                                    if (txtEmail != null) {
                                                                                                                                        txtEmail.text = displayEmail
                                                                                                                                    }

                                                                                                                                    val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                    val displayTelephone = "Telephone No.\n$telephoneNumber"
                                                                                                                                    if (txtTelephone != null) {
                                                                                                                                        txtTelephone.text = displayTelephone
                                                                                                                                    }

                                                                                                                                    val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                    if (btnOkAttendanceConfirm != null) {
                                                                                                                                        btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                            dialog6.dismiss()
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                                dialog5.show()
                                                                                                                dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                dialog5.window!!.setBackgroundDrawable(
                                                                                                                    ColorDrawable(
                                                                                                                        Color.TRANSPARENT)
                                                                                                                )
                                                                                                                dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                dialog5.window!!.setGravity(
                                                                                                                    Gravity.BOTTOM)

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            dialog4.show()
                                                                                            dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                            dialog4.window!!.setBackgroundDrawable(
                                                                                                ColorDrawable(
                                                                                                    Color.TRANSPARENT)
                                                                                            )
                                                                                            dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                            dialog4.window!!.setGravity(
                                                                                                Gravity.BOTTOM)

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    dialog3.show()
                                                                    dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                    dialog3.window!!.setBackgroundDrawable(
                                                                        ColorDrawable(Color.TRANSPARENT)
                                                                    )
                                                                    dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                    dialog3.window!!.setGravity(
                                                                        Gravity.BOTTOM)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            dialog2.show()
                                            dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                            dialog2.window!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    Color.TRANSPARENT)
                                            )
                                            dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                            dialog2.window!!.setGravity(Gravity.BOTTOM)

                                        } else if(editTxtFirstNameSignUp.text.toString().trim().isNotEmpty() && editTxtLastNameSignUp.text.toString().trim().isNotEmpty()){
                                            val passName = "${editTxtFirstNameSignUp.text.toString().trim().uppercase()} ${editTxtLastNameSignUp.text.toString().trim().uppercase()}"

                                            val dialog2 = BottomSheetDialog(v.context)
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            dialog2.setContentView(R.layout.bottomsheet_signuppersonaldetails)

                                            val genders = resources.getStringArray(R.array.Genders) //create variable to access listed items in string.xml
                                            val adapter1 = ArrayAdapter(v.context, R.layout.dropdown_item, genders)

                                            val spinnerGenderSignUp = dialog2.findViewById<AutoCompleteTextView>(R.id.spinnerGenderSignUp) //create variable to store spinner selection
                                            if (spinnerGenderSignUp != null) {
                                                spinnerGenderSignUp.setAdapter(adapter1)
                                            }

                                            val editTxtNationalIDNo = dialog2.findViewById<EditText>(R.id.editTxtNationalIDNo)
                                            val editTxtDateOfBirthSignUp = dialog2.findViewById<EditText>(R.id.editTxtDateOfBirthSignUp)
                                            if (editTxtDateOfBirthSignUp != null) {
                                                editTxtDateOfBirthSignUp.setOnClickListener {
                                                    val c = Calendar.getInstance()

                                                    val year1 = c.get(Calendar. YEAR)
                                                    val month = c.get(Calendar. MONTH)
                                                    val day = c.get(Calendar. DAY_OF_MONTH)

                                                    val datePickerDialog = DatePickerDialog(
                                                        v.context,
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
                                            }

                                            val btnCancelPersonalDetails = dialog2.findViewById<ImageView>(R.id.btnCancelPersonalDetails)
                                            if (btnCancelPersonalDetails != null) {
                                                btnCancelPersonalDetails.setOnClickListener{
                                                    dialog2.dismiss()
                                                }
                                            }

                                            val btnNextSignUpJobDescription = dialog2.findViewById<Button>(R.id.btnNextSignUpJobDescription)
                                            if (btnNextSignUpJobDescription != null) {
                                                btnNextSignUpJobDescription.setOnClickListener {
                                                    if (spinnerGenderSignUp != null) {
                                                        if(spinnerGenderSignUp.text.toString().isEmpty()){
                                                            Toast.makeText(v.context,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                                                        } else if (editTxtNationalIDNo != null) {
                                                            if (editTxtNationalIDNo.text.toString().trim().isEmpty()){
                                                                Toast.makeText(v.context,"NATIONAL ID REQUIRED", Toast.LENGTH_SHORT).show()
                                                            } else if (editTxtDateOfBirthSignUp != null) {
                                                                if ((editTxtDateOfBirthSignUp.text.toString().isEmpty())){
                                                                    Toast.makeText(v.context,"DATE OF BIRTH REQUIRED", Toast.LENGTH_SHORT).show()
                                                                } else{
                                                                    val passGender = spinnerGenderSignUp.text.toString()
                                                                    val passNationalId = editTxtNationalIDNo.text.toString().trim()
                                                                    val passDateOfBirth = editTxtDateOfBirthSignUp.text.toString()

                                                                    val dialog3 = BottomSheetDialog(v.context)
                                                                    dialog3.requestWindowFeature(
                                                                        Window.FEATURE_NO_TITLE)
                                                                    dialog3.setContentView(R.layout.bottomsheet_signupjobdescription)

                                                                    val editTxtOfficeBranchSignUp = dialog3.findViewById<EditText>(R.id.editTxtOfficeBranchSignUp)
                                                                    val editTxtDepartmentSignUp = dialog3.findViewById<EditText>(R.id.editTxtDepartmentSignUp)
                                                                    val editTxtJobTitleSignUp = dialog3.findViewById<EditText>(R.id.editTxtJobTitleSignUp)

                                                                    val btnCancelJobDescription = dialog3.findViewById<ImageView>(R.id.btnCancelJobDescription)
                                                                    if (btnCancelJobDescription != null) {
                                                                        btnCancelJobDescription.setOnClickListener{
                                                                            dialog3.dismiss()
                                                                        }
                                                                    }

                                                                    val btnNextSignUpContactInformation = dialog3.findViewById<Button>(R.id.btnNextSignUpContactInformation)
                                                                    if (btnNextSignUpContactInformation != null) {
                                                                        btnNextSignUpContactInformation.setOnClickListener {
                                                                            if (editTxtOfficeBranchSignUp != null) {
                                                                                if (editTxtOfficeBranchSignUp.text.toString().trim().isEmpty()){
                                                                                    Toast.makeText(v.context,"OFFICE/SITE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                } else if (editTxtDepartmentSignUp != null) {
                                                                                    if (editTxtDepartmentSignUp.text.toString().trim().isEmpty()){
                                                                                        Toast.makeText(v.context,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                    } else if (editTxtJobTitleSignUp != null) {
                                                                                        if (editTxtJobTitleSignUp.text.toString().trim().isEmpty()){
                                                                                            Toast.makeText(v.context,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                        } else {
                                                                                            val passOfficeSiteBranch: String = editTxtOfficeBranchSignUp.text.toString().trim().uppercase()
                                                                                            val passDepartment = editTxtDepartmentSignUp.text.toString().trim().uppercase()
                                                                                            val passJobTitle = editTxtJobTitleSignUp.text.toString().trim().uppercase()

                                                                                            val dialog4 = BottomSheetDialog(v.context)
                                                                                            dialog4.requestWindowFeature(
                                                                                                Window.FEATURE_NO_TITLE)
                                                                                            dialog4.setContentView(R.layout.bottomsheet_signupcontactinformation)

                                                                                            val editTxtTelephoneNumberSignUp = dialog4.findViewById<EditText>(R.id.editTxtTelephoneNumberSignUp)
                                                                                            val editTxtEmailAddressSignUp = dialog4.findViewById<EditText>(R.id.editTxtEmailAddressSignUp)

                                                                                            val btnCancelContactInformation = dialog4.findViewById<ImageView>(R.id.btnCancelContactInformation)
                                                                                            if (btnCancelContactInformation != null) {
                                                                                                btnCancelContactInformation.setOnClickListener{
                                                                                                    dialog4.dismiss()
                                                                                                }
                                                                                            }

                                                                                            val btnNextSignUpSetPIN = dialog4.findViewById<Button>(R.id.btnNextSignUpSetPIN)
                                                                                            if (btnNextSignUpSetPIN != null) {
                                                                                                btnNextSignUpSetPIN.setOnClickListener {
                                                                                                    if (editTxtTelephoneNumberSignUp != null) {
                                                                                                        if (editTxtTelephoneNumberSignUp.text.toString().trim().isEmpty()){
                                                                                                            Toast.makeText(v.context,"TELEPHONE NO. REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                        } else if (editTxtEmailAddressSignUp != null) {
                                                                                                            if(editTxtEmailAddressSignUp.text.toString().trim().isEmpty()){
                                                                                                                Toast.makeText(v.context,"EMAIL ADDRESS REQUIRED", Toast.LENGTH_SHORT).show()
                                                                                                            } else{
                                                                                                                val passEmailAddress = editTxtEmailAddressSignUp.text.toString().trim().lowercase()
                                                                                                                val passTelephoneNumber = editTxtTelephoneNumberSignUp.text.toString().trim()

                                                                                                                val dialog5 = BottomSheetDialog(v.context)
                                                                                                                dialog5.requestWindowFeature(
                                                                                                                    Window.FEATURE_NO_TITLE)
                                                                                                                dialog5.setContentView(R.layout.bottomsheet_signupsetpin)

                                                                                                                val editTxtPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtPINSignUp)
                                                                                                                val editTxtConfirmPINSignUp = dialog5.findViewById<EditText>(R.id.editTxtConfirmPINSignUp)

                                                                                                                val btnCancelSetPin = dialog5.findViewById<ImageView>(R.id.btnCancelSetPin)
                                                                                                                if (btnCancelSetPin != null) {
                                                                                                                    btnCancelSetPin.setOnClickListener{
                                                                                                                        dialog5.dismiss()
                                                                                                                    }
                                                                                                                }

                                                                                                                val btnFinishSignUp = dialog5.findViewById<Button>(R.id.btnFinishSignUp)
                                                                                                                if (btnFinishSignUp != null) {
                                                                                                                    btnFinishSignUp.setOnClickListener {
                                                                                                                        if (editTxtPINSignUp != null) {
                                                                                                                            if (editTxtPINSignUp.text.toString().trim().length != 4){
                                                                                                                                Toast.makeText(v.context,"4-DIGIT PIN REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                            } else if (editTxtConfirmPINSignUp != null) {
                                                                                                                                if(editTxtPINSignUp.text.toString().trim() != editTxtConfirmPINSignUp.text.toString().trim()) {
                                                                                                                                    Toast.makeText(v.context,"MATCHING PINS REQUIRED",Toast.LENGTH_SHORT).show()
                                                                                                                                } else{
                                                                                                                                    val emailAddress = passEmailAddress
                                                                                                                                    val telephoneNumber = passTelephoneNumber
                                                                                                                                    val officeSiteBranch = passOfficeSiteBranch
                                                                                                                                    val department = passDepartment
                                                                                                                                    val jobTitle = passJobTitle
                                                                                                                                    val gender = passGender
                                                                                                                                    val nationalId = passNationalId
                                                                                                                                    val dateOfBirth = passDateOfBirth
                                                                                                                                    val name = passName
                                                                                                                                    val accountTag = accountTag1
                                                                                                                                    val pinNumber: String = editTxtConfirmPINSignUp.text.toString().trim()
                                                                                                                                    val company = "to be set"
                                                                                                                                    val companyInitials = "to be set"
                                                                                                                                    val companyAdmissionKey = "to be set"

                                                                                                                                    val db = DBHelper(v.context, null)
                                                                                                                                    db.addDetails(name, accountTag, gender, nationalId, dateOfBirth, officeSiteBranch, department, jobTitle, emailAddress, telephoneNumber, pinNumber, company, companyInitials, companyAdmissionKey)

                                                                                                                                    dialog.dismiss()
                                                                                                                                    dialog1.dismiss()
                                                                                                                                    dialog2.dismiss()
                                                                                                                                    dialog3.dismiss()
                                                                                                                                    dialog4.dismiss()
                                                                                                                                    dialog5.dismiss()

                                                                                                                                    val dialog6 = MaterialAlertDialogBuilder(v.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmaccount).show()

                                                                                                                                    val txtName = dialog6.findViewById<TextView>(R.id.txtName)
                                                                                                                                    val displayName = "Name\n$name"
                                                                                                                                    if (txtName != null) {
                                                                                                                                        txtName.text = displayName
                                                                                                                                    }

                                                                                                                                    val txtGender = dialog6.findViewById<TextView>(R.id.txtGender)
                                                                                                                                    val displayGender = "Gender\n$gender"
                                                                                                                                    if (txtGender != null) {
                                                                                                                                        txtGender.text = displayGender
                                                                                                                                    }

                                                                                                                                    val txtNationalId = dialog6.findViewById<TextView>(R.id.txtNationalId)
                                                                                                                                    val displayNationalId = "National ID No.\n$nationalId"
                                                                                                                                    if (txtNationalId != null) {
                                                                                                                                        txtNationalId.text = displayNationalId
                                                                                                                                    }

                                                                                                                                    val txtDateOfBirth = dialog6.findViewById<TextView>(R.id.txtDateOfBirth)
                                                                                                                                    val displayDateOfBirth = "Date of Birth\n$dateOfBirth"
                                                                                                                                    if (txtDateOfBirth != null) {
                                                                                                                                        txtDateOfBirth.text = displayDateOfBirth
                                                                                                                                    }

                                                                                                                                    val txtSiteBranch = dialog6.findViewById<TextView>(R.id.txtSiteBranch)
                                                                                                                                    val displaySiteBranch = "Office/Site Branch\n$officeSiteBranch"
                                                                                                                                    if (txtSiteBranch != null) {
                                                                                                                                        txtSiteBranch.text = displaySiteBranch
                                                                                                                                    }

                                                                                                                                    val txtDepartment = dialog6.findViewById<TextView>(R.id.txtDepartment)
                                                                                                                                    val displayDepartment = "Department\n$department"
                                                                                                                                    if (txtDepartment != null) {
                                                                                                                                        txtDepartment.text = displayDepartment
                                                                                                                                    }

                                                                                                                                    val txtJobTitle = dialog6.findViewById<TextView>(R.id.txtJobTitle)
                                                                                                                                    val displayJobTitle = "Job Title\n$jobTitle"
                                                                                                                                    if (txtJobTitle != null) {
                                                                                                                                        txtJobTitle.text = displayJobTitle
                                                                                                                                    }

                                                                                                                                    val txtEmail = dialog6.findViewById<TextView>(R.id.txtEmail)
                                                                                                                                    val displayEmail = "E-Mail Address\n$emailAddress"
                                                                                                                                    if (txtEmail != null) {
                                                                                                                                        txtEmail.text = displayEmail
                                                                                                                                    }

                                                                                                                                    val txtTelephone = dialog6.findViewById<TextView>(R.id.txtTelephone)
                                                                                                                                    val displayTelephone = "Telephone No.\n$telephoneNumber"
                                                                                                                                    if (txtTelephone != null) {
                                                                                                                                        txtTelephone.text = displayTelephone
                                                                                                                                    }

                                                                                                                                    val btnOkAttendanceConfirm = dialog6.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                                                                                                    if (btnOkAttendanceConfirm != null) {
                                                                                                                                        btnOkAttendanceConfirm.setOnClickListener {
                                                                                                                                            dialog6.dismiss()
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                                dialog5.show()
                                                                                                                dialog5.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                                                dialog5.window!!.setBackgroundDrawable(
                                                                                                                    ColorDrawable(
                                                                                                                        Color.TRANSPARENT)
                                                                                                                )
                                                                                                                dialog5.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                                                dialog5.window!!.setGravity(
                                                                                                                    Gravity.BOTTOM)

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            dialog4.show()
                                                                                            dialog4.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                                            dialog4.window!!.setBackgroundDrawable(
                                                                                                ColorDrawable(
                                                                                                    Color.TRANSPARENT)
                                                                                            )
                                                                                            dialog4.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                                            dialog4.window!!.setGravity(
                                                                                                Gravity.BOTTOM)

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    dialog3.show()
                                                                    dialog3.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                                                    dialog3.window!!.setBackgroundDrawable(
                                                                        ColorDrawable(Color.TRANSPARENT)
                                                                    )
                                                                    dialog3.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                                                    dialog3.window!!.setGravity(
                                                                        Gravity.BOTTOM)


                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            dialog2.show()
                                            dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                            dialog2.window!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    Color.TRANSPARENT)
                                            )
                                            dialog2.window!!.attributes.windowAnimations = R.style.DialogAnimation
                                            dialog2.window!!.setGravity(Gravity.BOTTOM)
                                        }
                                    }
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
        return v
    }

}