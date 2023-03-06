package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileViewActivity : AppCompatActivity() {
    private lateinit var txtViewNameView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileview)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        val nameFilter = bundle!!.getString("NamePass").toString()

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val accountTagPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))
                val idNoPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val dateOfBirthPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE_OF_BIRTH))
                val officeSitePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                val telephonePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TELEPHONE_NUMBER))
                val emailPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_ADDRESS_NAME))

                if(namePrint.toString() == nameFilter){
                    val name = namePrint.toString()
                    val accountTag = accountTagPrint.toString()
                    val id = idNoPrint.toString()
                    val dateOfBirth = dateOfBirthPrint.toString()
                    val officeSite = officeSitePrint.toString()
                    val department = departmentPrint.toString()
                    val jobTitle = jobTitlePrint.toString()
                    val telephone = telephonePrint.toString()
                    val email = emailPrint.toString()

                    val txtViewAccountTagView = findViewById<TextView>(R.id.txtViewAccountTagView)
                    txtViewAccountTagView.text= accountTag

                    txtViewNameView = findViewById(R.id.txtViewNameView)
                    txtViewNameView.text = name

                    val txtViewNationalIdView = findViewById<TextView>(R.id.txtViewNationalIdView)
                    val displayId = "NATIONAL ID: $id"
                    txtViewNationalIdView.text = displayId

                    val txtViewDateOfBirthView = findViewById<TextView>(R.id.txtViewDateOfBirthView)
                    val displayDOB = "DATE OF BIRTH: $dateOfBirth"
                    txtViewDateOfBirthView.text = displayDOB

                    val txtViewOfficeSiteView = findViewById<TextView>(R.id.txtViewOfficeSiteView)
                    val displayOfficeSite = "OFFICE/SITE: $officeSite"
                    txtViewOfficeSiteView.text = displayOfficeSite

                    val txtViewDepartmentView = findViewById<TextView>(R.id.txtViewDepartmentView)
                    val displayDepartment = "DEPARTMENT: $department"
                    txtViewDepartmentView.text = displayDepartment

                    val txtViewJobTitleView = findViewById<TextView>(R.id.txtViewJobTitleView)
                    val displayJobTitle = "JOB TITLE: $jobTitle"
                    txtViewJobTitleView.text = displayJobTitle

                    val txtViewEmailAddressView = findViewById<TextView>(R.id.txtViewEmailAddressView)
                    val displayEmail = "E-MAIL: $email"
                    txtViewEmailAddressView.text = displayEmail

                    val txtViewTelephoneNumberView = findViewById<TextView>(R.id.txtViewTelephoneNumberView)
                    val displayTelephone = "PHONE NO.: $telephone"
                    txtViewTelephoneNumberView.text = displayTelephone

                }
            } while(cursor.moveToNext())
        }
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
                val passName = txtViewNameView.text.toString()
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
                val passName = txtViewNameView.text.toString()
                val passStartDate = "01-01-2000"
                val passEndDate = "31-12-2100"

                intent.putExtra("FullName",passName)
                intent.putExtra("StartDate",passStartDate)
                intent.putExtra("EndDate",passEndDate)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}