package com.kronos.timecardapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
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

class SecurityEditActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowPINEdit: CardView
    private lateinit var editTxtOldPINEdit: EditText
    private lateinit var editTxtNewPINEdit: EditText
    private lateinit var editTxtConfirmNewPINEdit: EditText
    private lateinit var btnSavePINEdit: Button
    private lateinit var btnCancelPINEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_securityedit)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NameVerify").toString()

        cardViewPopUpWindowPINEdit = findViewById(R.id.cardViewPopUpWindowPINEdit)
        editTxtOldPINEdit = findViewById(R.id.editTxtOldPINEdit)
        editTxtNewPINEdit = findViewById(R.id.editTxtNewPINEdit)
        editTxtConfirmNewPINEdit = findViewById(R.id.editTxtConfirmNewPINEdit)

        btnSavePINEdit = findViewById(R.id.btnSavePINEdit)
        btnSavePINEdit.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getLoginDetails()

            if(cursor!!.moveToFirst()){
                do{
                   val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                   val pinPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))

                   if(nameVerify == namePrint.toString()){
                       val oldPin = pinPrint.toString()

                       if(editTxtOldPINEdit.text.toString().trim() != oldPin){
                           Toast.makeText(this,"INCORRECT CURRENT PIN", Toast.LENGTH_SHORT).show()
                       } else if(editTxtNewPINEdit.text.toString().trim().length != 4){
                           Toast.makeText(this,"4-DIGIT PIN REQUIRED", Toast.LENGTH_SHORT).show()
                       } else if(editTxtNewPINEdit.text.toString().trim() != editTxtConfirmNewPINEdit.text.toString().trim()){
                           Toast.makeText(this,"MATCHING PINS REQUIRED", Toast.LENGTH_SHORT).show()
                       } else if(oldPin == editTxtOldPINEdit.text.toString().trim()){
                           if(cursor.moveToFirst()){
                               do{
                                   val namePrint1 = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                   val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                                   if(nameVerify == namePrint1.toString() && editTxtNewPINEdit.text.toString().trim() == editTxtConfirmNewPINEdit.text.toString().trim()){
                                       val id = idPrint.toLong()

                                       val savePIN = editTxtConfirmNewPINEdit.text.toString().trim()

                                       db.updatePIN(id, savePIN)
                                       db.close()

                                       val intent = Intent(this,LoginActivity::class.java)
                                       startActivity(intent)

                                       Toast.makeText(this,"$nameVerify: CHANGED PIN", Toast.LENGTH_SHORT).show()
                                   }
                               } while(cursor.moveToNext())
                           }
                           cursor.close()
                       }
                   }
                } while(cursor.moveToNext())
            }
            cursor.close()
        }

        btnCancelPINEdit = findViewById(R.id.btnCancelPINEdit)
        btnCancelPINEdit.setOnClickListener {
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
            cardViewPopUpWindowPINEdit.setBackgroundColor(animator.animatedValue as Int)
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
            cardViewPopUpWindowPINEdit.setBackgroundColor(animator.animatedValue as Int)
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