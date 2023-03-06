package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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

        val nameFilter = intent.getStringExtra("NameCheck").toString()

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

                if((nameFilter.isEmpty() || nameFilter == namePrint.toString())){
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

        /*val cardProfile = recyclerViewProfile.findViewById<CardView>(R.id.cardProfile)
        val txtViewNameProfile = recyclerViewProfile.findViewById<TextView>(R.id.txtViewNameProfile)

        val clickListener: View.OnClickListener = View.OnClickListener { view ->
            when(view.id){
                R.id.cardProfile -> {
                    val intent1 = Intent(this,ProfileViewActivity::class.java)
                    val passName = txtViewNameProfile.text.toString()

                    intent.putExtra("NamePass",passName)
                    startActivity(intent1)
                }
            }
        }
        cardProfile.setOnClickListener(clickListener)*/
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}