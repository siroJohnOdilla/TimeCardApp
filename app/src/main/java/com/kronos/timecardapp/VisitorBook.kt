package com.kronos.timecardapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class VisitorBook : Fragment() {
    private lateinit var spinnerVisitorBook: Spinner
    private lateinit var txtDisplayCompanyNameVisitorBook: TextView
    private lateinit var txtDisplayOfficeSiteVisitorBook: TextView
    private lateinit var editTxtDateStartVisitorBook: EditText
    private lateinit var editTxtDateEndVisitorBook: EditText
    private lateinit var btnGenerateVisitorBook: Button
    private lateinit var visitorList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val v =  inflater.inflate(R.layout.fragment_visitorbook, container, false)

        val bundle = arguments

        val displayCompanyName = bundle!!.getString("companyName")
        val displayOfficeSite = bundle.getString("officeSite")

        txtDisplayCompanyNameVisitorBook = v.findViewById(R.id.txtDisplayCompanyNameVisitorBook)
        txtDisplayCompanyNameVisitorBook.text = displayCompanyName

        txtDisplayOfficeSiteVisitorBook = v.findViewById(R.id.txtDisplayOfficeSiteVisitorBook)
        txtDisplayOfficeSiteVisitorBook.text = displayOfficeSite

        visitorList = ArrayList()
        visitorList.add("")

        val db = DBHelper4(v.context, null)
        val cursor = db.getDetails()

        if(cursor!!.moveToFirst()){
            do{
                val nameCheck = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))

                if(nameCheck.toString().isNotEmpty()){
                    val name = nameCheck.toString()
                    visitorList.add(name)
                    visitorList.add("")
                }
            } while(cursor.moveToNext())
        }
        cursor.close()

        spinnerVisitorBook = v.findViewById(R.id.spinnerVisitorBookName)
        if (spinnerVisitorBook != null){
            val adapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_item, visitorList)
            spinnerVisitorBook.adapter = adapter

            spinnerVisitorBook.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        editTxtDateStartVisitorBook = v.findViewById(R.id.editTxtDateStartVisitorBook)
        editTxtDateStartVisitorBook.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateStartVisitorBook.setText(date)
                },
                year1,
                month,
                day
            )

            datePickerDialog.show()
        }

        editTxtDateEndVisitorBook = v.findViewById(R.id.editTxtDateEndVisitorBook)
        editTxtDateEndVisitorBook.setOnClickListener {
            val c = Calendar.getInstance()

            val year1 = c.get(Calendar. YEAR)
            val month = c.get(Calendar. MONTH)
            val day = c.get(Calendar. DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                v.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val date: String = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year)
                    editTxtDateEndVisitorBook.setText(date)
                },
                year1,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnGenerateVisitorBook = v.findViewById(R.id.btnGenerateVisitBook)
        btnGenerateVisitorBook.setOnClickListener {

            val intent = Intent(v.context,VisitorBookViewActivity::class.java)

            val passStartDate = editTxtDateStartVisitorBook.text.toString()
            val passEndDate = editTxtDateEndVisitorBook.text.toString()
            val passName = spinnerVisitorBook.selectedItem.toString()

            intent.putExtra("StartDate",passStartDate)
            intent.putExtra("EndDate",passEndDate)
            intent.putExtra("FullName",passName)

            editTxtDateStartVisitorBook.text.clear()
            editTxtDateEndVisitorBook.text.clear()

            startActivity(intent)
            Toast.makeText(v.context,"OPENING VISITOR'S BOOK...",Toast.LENGTH_SHORT).show()
        }
        return v
    }


}