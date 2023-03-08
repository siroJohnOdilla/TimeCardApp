package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileViewListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileviewlist)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        val nameFilter1 = intent.getStringExtra("NameCheck").toString()

        val recyclerViewProfile = findViewById<RecyclerView>(R.id.recyclerViewProfile)
        recyclerViewProfile.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsViewModel>()

        val db = DBHelper(this, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                val officeSiteBranchPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                val nationalIdPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val accountTagPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                if((nameFilter1.isEmpty() || nameFilter1 == namePrint.toString())){
                    val name = namePrint.toString()
                    val jobTitle = "$jobTitlePrint ($departmentPrint)"
                    val officeSiteBranch = officeSiteBranchPrint.toString()
                    val idNo = nationalIdPrint.toString()
                    val accountTag = accountTagPrint.toString()

                    data.add(ItemsViewModel(name, jobTitle, officeSiteBranch, idNo, accountTag))

                }
            } while(cursor.moveToNext())
        }
        val adapter = CustomAdapter(data)
        recyclerViewProfile.adapter = adapter

        adapter.setOnClickListener(object: CustomAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@ProfileViewListActivity,"FETCHING PROFILE INFORMATION...",Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}