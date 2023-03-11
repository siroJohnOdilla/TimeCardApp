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

class DeleteProfileActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowDelete: CardView
    private lateinit var txtViewDisplayMessage: TextView
    private lateinit var editTxtLeaveAdminKeyDelete: EditText
    private lateinit var btnYesDelete: Button
    private lateinit var btnNoDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_deleteprofile)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NameVerify").toString()

        cardViewPopUpWindowDelete = findViewById(R.id.cardViewPopUpWindowDelete)

        txtViewDisplayMessage = findViewById(R.id.txtViewDisplayMessage)
        val displayMessage = "DELETE $nameVerify\nAre you sure?"
        txtViewDisplayMessage.text = displayMessage

        editTxtLeaveAdminKeyDelete = findViewById(R.id.editTxtLeaveAdminKeyDelete)

        btnYesDelete = findViewById(R.id.btnYesDelete)
        btnYesDelete.setOnClickListener {
            val adminKeyDelete = "h4ck-th3-pl4n3t"
            if(editTxtLeaveAdminKeyDelete.text.toString().trim() != adminKeyDelete){
                Toast.makeText(this,"ENTER VALID ADMIN KEY",Toast.LENGTH_SHORT).show()
            } else if(editTxtLeaveAdminKeyDelete.text.toString().trim() == adminKeyDelete){
                val db = DBHelper(this, null)
                val cursor = db.getLoginDetails()

                if(cursor!!.moveToFirst()){
                    do{
                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))

                        if(nameVerify == namePrint.toString()){
                            val name = namePrint.toString()

                            db.deleteProfile(name)
                            db.close()

                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this,"$name;\nSUCCESSFULLY DELETED ", Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }

        btnNoDelete = findViewById(R.id.btnNoDelete)
        btnNoDelete.setOnClickListener {
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
            cardViewPopUpWindowDelete.setBackgroundColor(animator.animatedValue as Int)
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
            cardViewPopUpWindowDelete.setBackgroundColor(animator.animatedValue as Int)
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