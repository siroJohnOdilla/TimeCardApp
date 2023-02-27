package com.kronos.timecardapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        val recyclerViewProfile = v.findViewById<RecyclerView>(R.id.recyclerViewProfile)
        recyclerViewProfile.layoutManager = LinearLayoutManager(v.context)

        val data = ArrayList<ItemsViewModel>()

        val db = DBHelper(v.context, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                val jobTitlePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE_NAME))
                val departmentPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTMENT_NAME))
                val officeSiteBranchPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.OFFICE_SITE_BRANCH))
                val nationalIdPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NATIONAL_ID))
                val accountTagPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNT_TAG))

                if(namePrint.toString().isNotEmpty() && jobTitlePrint.toString().isNotEmpty() && departmentPrint.toString().isNotEmpty() && officeSiteBranchPrint.toString().isNotEmpty() && nationalIdPrint.toString().isNotEmpty() && accountTagPrint.toString().isNotEmpty()){
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

        return v
    }

}