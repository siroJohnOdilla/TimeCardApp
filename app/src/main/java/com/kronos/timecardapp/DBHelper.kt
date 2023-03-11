package com.kronos.timecardapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper (context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                     + ID_COL + " INTEGER PRIMARY KEY, " +
                     NAME_COL + " TEXT, " +
                     ACCOUNT_TAG + " TEXT, " +
                     GENDER + " TEXT, " +
                     NATIONAL_ID + " TEXT, " +
                     DATE_OF_BIRTH + " TEXT, " +
                     OFFICE_SITE_BRANCH + " TEXT, " +
                     DEPARTMENT_NAME + " TEXT, " +
                     JOB_TITLE_NAME + " TEXT, " +
                     EMAIL_ADDRESS_NAME + " TEXT, " +
                     TELEPHONE_NUMBER + " TEXT, " +
                     PIN_NUMBER + " TEXT, " +
                     COMPANY_NAME + " TEXT, " +
                     COMPANY_INITIALS + " TEXT, " +
                     COMPANY_ADM_KEY + " TEXT " + " )" )
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addDetails(name: String,
                accountTag: String,
                gender: String,
                nationalId: String,
                dateOfBirth: String,
                officeSiteBranch: String,
                department: String,
                jobTitle: String,
                emailAddress: String,
                telephoneNumber: String,
                pinNumber: String,
                company: String,
                companyInitials: String,
                companyAdmissionKey: String){
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(ACCOUNT_TAG, accountTag)
        values.put(GENDER, gender)
        values.put(NATIONAL_ID, nationalId)
        values.put(DATE_OF_BIRTH, dateOfBirth)
        values.put(OFFICE_SITE_BRANCH, officeSiteBranch)
        values.put(DEPARTMENT_NAME, department)
        values.put(JOB_TITLE_NAME, jobTitle)
        values.put(EMAIL_ADDRESS_NAME, emailAddress)
        values.put(TELEPHONE_NUMBER, telephoneNumber)
        values.put(PIN_NUMBER, pinNumber)
        values.put(COMPANY_NAME, company)
        values.put(COMPANY_INITIALS, companyInitials )
        values.put(COMPANY_ADM_KEY, companyAdmissionKey)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateCompany(id: Long, company: String, companyInitials: String, companyAdmissionKey: String): Int{
        val values = ContentValues()
        values.put(COMPANY_NAME, company)
        values.put(COMPANY_INITIALS, companyInitials)
        values.put(COMPANY_ADM_KEY, companyAdmissionKey)
        val whereclause = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclause, whereargs)
    }
    fun updateAccountTag(id: Long, saveAccountTag: String) : Int{
        val values = ContentValues()
        values.put(ACCOUNT_TAG,saveAccountTag)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun updateName(id: Long, saveName: String) : Int{
        val values = ContentValues()
        values.put(NAME_COL,saveName)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun updatePersonalDetails(id: Long, saveGender: String, saveNationalId: String, saveDateOfBirth: String) : Int{
        val values = ContentValues()
        values.put(GENDER,saveGender)
        values.put(NATIONAL_ID,saveNationalId)
        values.put(DATE_OF_BIRTH,saveDateOfBirth)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun updateJobDescription(id: Long, saveOfficeSiteBranch: String, saveDepartment: String, saveJobTitle: String) : Int{
        val values = ContentValues()
        values.put(OFFICE_SITE_BRANCH,saveOfficeSiteBranch)
        values.put(DEPARTMENT_NAME,saveDepartment)
        values.put(JOB_TITLE_NAME,saveJobTitle)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun updateContactInformation(id: Long, saveEmailAddress: String, saveTelephoneNumber: String) : Int{
        val values = ContentValues()
        values.put(EMAIL_ADDRESS_NAME,saveEmailAddress)
        values.put(TELEPHONE_NUMBER,saveTelephoneNumber)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun updatePIN(id: Long, savePIN: String) : Int{
        val values = ContentValues()
        values.put(PIN_NUMBER,savePIN)
        val whereclaus = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclaus, whereargs)
    }
    fun deleteProfile(deleteName: String){
        val whereclause = "$NAME_COL=?"
        val whereargs = arrayOf(deleteName)
        val db = this.writableDatabase
        db.delete(TABLE_NAME, whereclause, whereargs)
        db.close()
    }

    fun getLoginDetails() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null,null)
    }
    companion object {
        private const val DATABASE_NAME = "EMPLOYEE DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "employee_table"
        const val ID_COL = "id"
        const val NAME_COL = "name"
        const val ACCOUNT_TAG = "accountTag"
        const val GENDER = "gender"
        const val NATIONAL_ID = "nationalId"
        const val DATE_OF_BIRTH = "dateOfBirth"
        const val OFFICE_SITE_BRANCH = "officeSiteBranch"
        const val DEPARTMENT_NAME = "department"
        const val JOB_TITLE_NAME = "jobTitle"
        const val EMAIL_ADDRESS_NAME = "emailAddress"
        const val TELEPHONE_NUMBER = "telephoneNumber"
        const val PIN_NUMBER = "pinNumber"
        const val COMPANY_NAME = "companyName"
        const val COMPANY_INITIALS = "companyInitials"
        const val COMPANY_ADM_KEY = "companyAdmissionKey"
    }
}