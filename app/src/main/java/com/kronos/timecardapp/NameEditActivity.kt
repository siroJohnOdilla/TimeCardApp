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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils

class NameEditActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowNameEdit: CardView
    private lateinit var editTxtFirstNameEdit: EditText
    private lateinit var editTxtMiddleNameEdit: EditText
    private lateinit var editTxtLastNameEdit: EditText
    private lateinit var btnSaveNameTagEdit: Button
    private lateinit var btnCancelNameTagEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_nameedit)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NameVerify").toString()

        cardViewPopUpWindowNameEdit = findViewById(R.id.cardViewPopUpWindowNameEdit)
        editTxtFirstNameEdit = findViewById(R.id.editTxtFirstNameEdit)
        editTxtMiddleNameEdit = findViewById(R.id.editTxtMiddleNameEdit)
        editTxtLastNameEdit = findViewById(R.id.editTxtLastNameEdit)

        btnSaveNameTagEdit = findViewById(R.id.btnSaveNameEdit)
        btnSaveNameTagEdit.setOnClickListener {
            if(editTxtFirstNameEdit.text.toString().trim().uppercase().isEmpty()){
                Toast.makeText(this,"FIRST NAME REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtLastNameEdit.text.toString().trim().uppercase().isEmpty()){
                Toast.makeText(this,"LAST NAME REQUIRED",Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this, null)
                val cursor = db.getLoginDetails()

                if(cursor!!.moveToFirst()){
                    do{
                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                        if(nameVerify == namePrint.toString()){
                            val id = idPrint.toLong()
                            val firstName = editTxtFirstNameEdit.text.toString().trim().uppercase()
                            val middleName = editTxtMiddleNameEdit.text.toString().trim().uppercase()
                            val lastName = editTxtLastNameEdit.text.toString().trim().uppercase()

                            val saveName = "$firstName $middleName $lastName"

                            db.updateName(id, saveName)
                            db.close()

                            Toast.makeText(this,"SAVED", Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnCancelNameTagEdit = findViewById(R.id.btnCancelNameEdit)
        btnCancelNameTagEdit.setOnClickListener {
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
            cardViewPopUpWindowNameEdit.setBackgroundColor(animator.animatedValue as Int)
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
            cardViewPopUpWindowNameEdit.setBackgroundColor(animator.animatedValue as Int)
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