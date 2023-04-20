package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class Profile : Fragment() {
    private lateinit var employeeList: ArrayList<String>
    private lateinit var spinnerProfileEmployeeName: AutoCompleteTextView
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
            Toast.makeText(v.context,"FETCHING PROFILES...",Toast.LENGTH_SHORT).show()
        }
        return v
    }

}