package com.kronos.timecardapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Profile : Fragment() {
    private lateinit var employeeList: ArrayList<String>
    private lateinit var spinnerProfileEmployeeName: Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        employeeList = ArrayList()
        employeeList.add("")

        val db = DBHelper(v.context, null)
        val cursor = db.getLoginDetails()

        if(cursor!!.moveToFirst()){
            do{
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                if(nameCheck.toString().isNotEmpty()){
                    val name = nameCheck.toString()
                    employeeList.add(name)
                    employeeList.add("")
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        spinnerProfileEmployeeName = v.findViewById(R.id.spinnerProfileEmployeeName)
        if (spinnerProfileEmployeeName != null){
            val adapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, employeeList)
            spinnerProfileEmployeeName.adapter = adapter

            spinnerProfileEmployeeName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,getString(R.string.selected_item) + " " + " " + genders[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Toast.makeText(this@SignUpPersonalDetailsActivity,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val btnGenerateProfiles = v.findViewById<Button>(R.id.btnGenerateProfiles)
        btnGenerateProfiles.setOnClickListener {
            val intent = Intent(v.context,ProfileViewListActivity::class.java)

            val passName = spinnerProfileEmployeeName.selectedItem.toString()

            intent.putExtra("NameCheck",passName)
            startActivity(intent)
            Toast.makeText(v.context,"FETCHING PROFILES...",Toast.LENGTH_SHORT).show()
        }
        return v
    }

}