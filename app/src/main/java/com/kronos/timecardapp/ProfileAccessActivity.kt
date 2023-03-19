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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils

class ProfileAccessActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowPINProfileAccess: CardView
    private lateinit var txtViewDisplayMessageProfileAccess: TextView
    private lateinit var editTxtEnterPINProfileAccess: EditText
    private lateinit var btnEnterPINProfileAccess: Button
    private lateinit var btnCloseProfileAccess: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_profileaccess)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NamePass").toString()

        cardViewPopUpWindowPINProfileAccess = findViewById(R.id.cardViewPopUpWindowPINProfileAccess)

        txtViewDisplayMessageProfileAccess = findViewById(R.id.txtViewDisplayMessageProfileAccess)
        val displayMessage = "ACCESSING:\n$nameVerify"
        txtViewDisplayMessageProfileAccess.text = displayMessage

        editTxtEnterPINProfileAccess = findViewById(R.id.editTxtEnterPINProfileAccess)

        btnEnterPINProfileAccess = findViewById(R.id.btnEnterPINProfileAccess)
        btnEnterPINProfileAccess.setOnClickListener {
            if(editTxtEnterPINProfileAccess.text.toString().trim().isEmpty()){
                Toast.makeText(this,"PROFILE PIN REQUIRED", Toast.LENGTH_SHORT).show()
            } else{
                val db = DBHelper(this, null)
                val cursor = db.getLoginDetails()

                if(cursor!!.moveToFirst()){
                    do{
                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val pinPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))

                        if(nameVerify == namePrint.toString() && editTxtEnterPINProfileAccess.text.toString().trim() == pinPrint.toString()){
                            val intent = Intent(this,ProfileViewActivity::class.java)

                            intent.putExtra("NamePass",nameVerify)
                            startActivity(intent)

                            Toast.makeText(this,"FETCHING PROFILE INFORMATION...",Toast.LENGTH_SHORT).show()
                        }

                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnCloseProfileAccess = findViewById(R.id.btnCloseProfileAccess)
        btnCloseProfileAccess.setOnClickListener {
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
            cardViewPopUpWindowPINProfileAccess.setBackgroundColor(animator.animatedValue as Int)
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
            cardViewPopUpWindowPINProfileAccess.setBackgroundColor(animator.animatedValue as Int)
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