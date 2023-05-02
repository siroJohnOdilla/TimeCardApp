package com.kronos.timecardapp

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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.collections.ArrayList

class ProfileViewActivity : AppCompatActivity() {
    private lateinit var getName: String
    private lateinit var titleList: ArrayList<String>
    private lateinit var infoList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileview)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        val nameFilter = bundle!!.getString("NamePass").toString()
        getName = nameFilter

        titleList = ArrayList()
        titleList.add("1.ACCOUNT TYPE")
        titleList.add("2.NAME")
        titleList.add("3.PERSONAL DETAILS")
        titleList.add("4.COMPANY")
        titleList.add("5.JOB DESCRIPTION")
        titleList.add("6.CONTACT INFORMATION")

        infoList = ArrayList()

        val recyclerViewDisplayProfile = findViewById<RecyclerView>(R.id.recyclerViewDisplayProfile)
        recyclerViewDisplayProfile.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemViewModel4>()

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val accountTagPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))
                val idNoPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val genderPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GENDER))
                val dateOfBirthPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE_OF_BIRTH))
                val companyPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_NAME))
                val companyInitialsPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMPANY_INITIALS))
                val officeSitePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                val telephonePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TELEPHONE_NUMBER))
                val emailPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_ADDRESS_NAME))

                if(namePrint.toString() == nameFilter){
                    val name = namePrint.toString()

                    val accountTag = accountTagPrint.toString()

                    val gender = genderPrint.toString()
                    val id = idNoPrint.toString()
                    val dateOfBirth = dateOfBirthPrint.toString()
                    val detailsJoined = "GENDER: $gender\n\nNATIONAL ID: $id\n\nDATE OF BIRTH: $dateOfBirth"

                    val company = companyPrint.toString()
                    val companyInitials = companyInitialsPrint.toString()
                    val companyJoined = "$company ($companyInitials)"

                    val officeSite = officeSitePrint.toString()
                    val department = departmentPrint.toString()
                    val jobTitle = jobTitlePrint.toString()
                    val jobJoined = "OFFICE SITE: $officeSite\n\nDEPARTMENT: $department\n\nJOB TITLE: $jobTitle"

                    val telephone = telephonePrint.toString()
                    val email = emailPrint.toString()
                    val contactJoined = "PHONE NO.: $telephone\n\nEMAIL: $email"

                    infoList.add(accountTag)
                    infoList.add(name)
                    infoList.add(detailsJoined)
                    infoList.add(companyJoined)
                    infoList.add(jobJoined)
                    infoList.add(contactJoined)

                    for(i in 0..5){
                        val title = titleList[i]
                        val info = infoList[i]

                        data.add(ItemViewModel4(title, info))
                    }
                }
            } while(cursor.moveToNext())
        }
        val adapter = CustomAdapter4(data)
        recyclerViewDisplayProfile.adapter = adapter

        adapter.setOnClickListener(object: CustomAdapter4.onItemClickListener{
            override fun onItemClick(position: Int) {
                getName = nameFilter
            }
        })
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
                val intent = Intent(this,AccountTagEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.subItemEditName -> {
                val intent = Intent(this,NameEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.subItemEditPersonalDetails -> {
                val intent = Intent(this,PersonalDetailsEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.subItemEditCompany -> {
                val intent = Intent(this,CompanyEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.subItemEditJobDescription -> {
                val intent = Intent(this,JobDescriptionEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.subItemEditContactInformation -> {
                val intent = Intent(this,ContactInformationEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.itemViewChangePIN -> {
                val intent = Intent(this,SecurityEditActivity::class.java)
                val passName = getName

                intent.putExtra("darkStatusBar", false)
                intent.putExtra("NameVerify",passName)
                startActivity(intent)
                return true
            }
            R.id.itemViewDeleteProfile -> {
                val dialog = BottomSheetDialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.bottomsheet_deleteprofile)

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
                dialog.show()
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                dialog.window!!.setGravity(Gravity.BOTTOM)
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