package com.kronos.timecardapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class MyProfile : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_myprofile, container, false)

        val bundle = arguments
        val passName = bundle!!.getString("passName").toString()

        val db = DBHelper(v.context, null)
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
                    txtProfileAccount = v.findViewById(R.id.txtProfileAccount)
                    val displayAccount = "Account\n$accountPrint"
                    txtProfileAccount.text = displayAccount

                    txtProfileName = v.findViewById(R.id.txtProfileName)
                    val displayName = "Name\n$namePrint"
                    txtProfileName.text = displayName

                    txtProfileGender = v.findViewById(R.id.txtProfileGender)
                    val displayGender = "Gender\n$genderPrint"
                    txtProfileGender.text = displayGender

                    txtProfileNationalId = v.findViewById(R.id.txtProfileNationalId)
                    val displayNationalId = "National ID No.\n$nationalIdPrint"
                    txtProfileNationalId.text = displayNationalId

                    txtProfileDateOfBirth = v.findViewById(R.id.txtProfileDateOfBirth)
                    val displayDateOfBirth = "Date of Birth\n$dateOfBirthPrint"
                    txtProfileDateOfBirth.text = displayDateOfBirth

                    txtProfileEmail = v.findViewById(R.id.txtProfileEmail)
                    val displayEmail = "E-Mail Address\n$emailPrint"
                    txtProfileEmail.text = displayEmail

                    txtProfileTelephoneNo = v.findViewById(R.id.txtProfilePhoneNo)
                    val displayPhoneNo = "Telephone No.\n$telephonePrint"
                    txtProfileTelephoneNo.text = displayPhoneNo

                    txtProfileCompany = v.findViewById(R.id.txtProfileCompany)
                    val displayCompany = "Company\n$companyPrint($companyInitialsPrint)"
                    txtProfileCompany.text = displayCompany

                    txtProfileSiteBranch = v.findViewById(R.id.txtProfileSiteBranch)
                    val displaySiteBranch = "Office/Site Branch\n$siteBranchPrint"
                    txtProfileSiteBranch.text = displaySiteBranch

                    txtProfileDepartment = v.findViewById(R.id.txtProfileDepartment)
                    val displayDepartment = "Department\n$departmentPrint"
                    txtProfileDepartment.text = displayDepartment

                    txtProfileJobTitle = v.findViewById(R.id.txtProfileJobTitle)
                    val displayJobTitle = "Job Title\n$jobTitlePrint"
                    txtProfileJobTitle.text = displayJobTitle
                }

            } while(cursor.moveToNext())
        }
        cursor.close()

        return v
    }
}