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

class JobDescriptionEditActivity : AppCompatActivity() {
    private var darkStatusBar = false
    private lateinit var cardViewPopUpWindowJobDescriptionEdit: CardView
    private lateinit var editTxtOfficeBranchEdit: EditText
    private lateinit var editTxtDepartmentEdit: EditText
    private lateinit var editTxtJobTitleEdit: EditText
    private lateinit var btnSaveJobDescriptionTagEdit: Button
    private lateinit var btnCancelJobDescriptionTagEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_jobdescriptionedit)

        val actionBar = supportActionBar
        actionBar!!.title = ""

        val bundle: Bundle? = intent.extras
        darkStatusBar = bundle!!.getBoolean("darkStatusBar", false)
        val nameVerify = bundle.getString("NameVerify").toString()

        cardViewPopUpWindowJobDescriptionEdit = findViewById(R.id.cardViewPopUpWindowJobDescriptionEdit)
        editTxtOfficeBranchEdit = findViewById(R.id.editTxtOfficeBranchEdit)
        editTxtDepartmentEdit = findViewById(R.id.editTxtDepartmentEdit)
        editTxtJobTitleEdit = findViewById(R.id.editTxtJobTitleEdit)

        btnSaveJobDescriptionTagEdit = findViewById(R.id.btnSaveJobDescriptionEdit)
        btnSaveJobDescriptionTagEdit.setOnClickListener {
            if(editTxtOfficeBranchEdit.text.toString().trim().uppercase().isEmpty()){
                Toast.makeText(this,"OFFICE/ SITE BRANCH REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtDepartmentEdit.text.toString().trim().uppercase().isEmpty()){
                Toast.makeText(this,"DEPARTMENT REQUIRED",Toast.LENGTH_SHORT).show()
            } else if(editTxtJobTitleEdit.text.toString().trim().uppercase().isEmpty()){
                Toast.makeText(this,"JOB TITLE REQUIRED",Toast.LENGTH_SHORT).show()
            } else{
                val db = DBHelper(this, null)
                val cursor = db.getLoginDetails()

                if(cursor!!.moveToFirst()){
                    do{
                        val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                        val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))

                        if(nameVerify == namePrint.toString()){
                            val id = idPrint.toLong()
                            val saveOfficeSiteBranch = editTxtOfficeBranchEdit.text.toString().trim().uppercase()
                            val saveDepartment = editTxtDepartmentEdit.text.toString().trim().uppercase()
                            val saveJobTitle = editTxtJobTitleEdit.text.toString().trim().uppercase()


                            db.updateJobDescription(id, saveOfficeSiteBranch, saveDepartment, saveJobTitle)
                            db.close()

                            Toast.makeText(this,"SAVED", Toast.LENGTH_SHORT).show()
                        }
                    } while(cursor.moveToNext())
                }
                cursor.close()
            }
        }
        btnCancelJobDescriptionTagEdit = findViewById(R.id.btnCancelJobDescriptionEdit)
        btnCancelJobDescriptionTagEdit.setOnClickListener {
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
            cardViewPopUpWindowJobDescriptionEdit.setBackgroundColor(animator.animatedValue as Int)
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
            cardViewPopUpWindowJobDescriptionEdit.setBackgroundColor(animator.animatedValue as Int)
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