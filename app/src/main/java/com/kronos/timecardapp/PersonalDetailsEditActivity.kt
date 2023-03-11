package com.kronos.timecardapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils

class PersonalDetailsEditActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowPersonalDetailsEdit: CardView
    private lateinit var spinnerGenderEdit: Spinner
    private lateinit var editTxtNationalIDNoEdit: EditText
    private lateinit var editTxtDateOfBirthEdit: EditText
    private lateinit var btnSavePersonalDetailsTagEdit: Button
    private lateinit var btnCancelPersonalDetailsTagEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_personaldetailsedit)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NameVerify").toString()

        cardViewPopUpWindowPersonalDetailsEdit = findViewById(R.id.cardViewPopUpWindowPersonalDetailsEdit)

        spinnerGenderEdit = findViewById(R.id.spinnerGenderEdit)
        val genders = resources.getStringArray(R.array.Genders)
        if (spinnerGenderEdit != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            spinnerGenderEdit.adapter = adapter

            spinnerGenderEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        editTxtNationalIDNoEdit = findViewById(R.id.editTxtNationalIDNoEdit)
        editTxtDateOfBirthEdit = findViewById(R.id.editTxtDateOfBirthEdit)

        btnSavePersonalDetailsTagEdit = findViewById(R.id.btnSavePersonalDetailsEdit)
        btnSavePersonalDetailsTagEdit.setOnClickListener {
            if(spinnerGenderEdit.selectedItem.toString().isEmpty()){
                Toast.makeText(this,"GENDER REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtNationalIDNoEdit.text.toString().isEmpty()){
                Toast.makeText(this,"NATIONAL ID NO. REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtDateOfBirthEdit.text.toString().isEmpty()){
                Toast.makeText(this,"DATE OF BIRTH REQUIRED",Toast.LENGTH_SHORT).show()
            } else{
                val db = DBHelper(this, null)
                val cursor = db.getLoginDetails()

                if(cursor!!.moveToFirst()){
                    do{
                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                        if(nameVerify == namePrint.toString()){
                            val id = idPrint.toLong()
                            val saveGender = spinnerGenderEdit.selectedItem.toString()
                            val saveNationalId = editTxtNationalIDNoEdit.text.toString().trim().uppercase()
                            val saveDateOfBirth = editTxtDateOfBirthEdit.text.toString().trim()

                            db.updatePersonalDetails(id, saveGender, saveNationalId, saveDateOfBirth)
                            db.close()

                            Toast.makeText(this,"PERSONAL DETAILS: SAVED", Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnCancelPersonalDetailsTagEdit = findViewById(R.id.btnCancelPersonalDetailsEdit)
        btnCancelPersonalDetailsTagEdit.setOnClickListener {
            onBackPressed()
        }
        if(Build.VERSION.SDK_INT in 19..20){
            setWindowFlag(this, true)
        }
        if(Build.VERSION.SDK_INT >= 19){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if(Build.VERSION.SDK_INT >= 21){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(darkStatusBar){
                    this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                this.window.statusBarColor = Color.TRANSPARENT
                setWindowFlag(this, false)
            }
        }
        val alpha = 100
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation =  ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500
        colorAnimation.addUpdateListener { animator ->
            cardViewPopUpWindowPersonalDetailsEdit.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val alpha = 100
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation =  ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500
        colorAnimation.addUpdateListener { animator ->
            cardViewPopUpWindowPersonalDetailsEdit.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }
    private fun setWindowFlag(activity: Activity, on: Boolean){
        val win = activity.window
        val winParams = win.attributes

        if(on){
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else{
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }
}