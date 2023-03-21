package com.kronos.timecardapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper4 (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                DATE_COL + " TEXT, " +
                NAME_COL + " TEXT, " +
                NATIONAL_ID_NO + " TEXT, " +
                TELEPHONE_N0 + " TEXT, " +
                COMPANY_NAME + " TEXT, " +
                CONTACT_PERSON + " TEXT, " +
                REASON_FOR_VISIT + " TEXT, " +
                TIME_IN + " TEXT, " +
                AUTHORIZED_TIME_IN + " TEXT, " +
                TIME_OUT + " TEXT, " +
                AUTHORIZED_TIME_OUT + " TEXT " + " )" )
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun getDetails() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null,null)
    }
    fun addVisitor(date: String,
                   name: String,
                   nationalId: String,
                   telephoneNumber: String,
                   company: String,
                   companyHost: String,
                   natureOfVisit: String,
                   timeIn: String,
                   authorizedTimeIn: String,
                   timeOut: String,
                   authorizedTimeOut: String ){
        val values = ContentValues()
        values.put(DATE_COL,date)
        values.put(NAME_COL,name)
        values.put(NATIONAL_ID_NO,nationalId)
        values.put(TELEPHONE_N0,telephoneNumber)
        values.put(COMPANY_NAME,company)
        values.put(CONTACT_PERSON,companyHost)
        values.put(REASON_FOR_VISIT,natureOfVisit)
        values.put(TIME_IN,timeIn)
        values.put(AUTHORIZED_TIME_IN,authorizedTimeIn)
        values.put(TIME_OUT,timeOut)
        values.put(AUTHORIZED_TIME_OUT,authorizedTimeOut)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateVisitor(id: Long, timeOut: String, authorizedTimeOut: String): Int{
        val values = ContentValues()
        values.put(TIME_OUT, timeOut)
        values.put(AUTHORIZED_TIME_OUT, authorizedTimeOut)
        val whereclause = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclause, whereargs)
    }
    companion object {
        private const val DATABASE_NAME = "VISITOR DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "visitor_table"
        const val ID_COL = "id"
        const val DATE_COL = "date"
        const val NAME_COL = "name"
        const val NATIONAL_ID_NO = "nationalId"
        const val TELEPHONE_N0 = "telephoneNo"
        const val COMPANY_NAME = "company"
        const val CONTACT_PERSON = "contactPerson"
        const val REASON_FOR_VISIT = "reasonForVisit"
        const val TIME_IN = "timeIn"
        const val AUTHORIZED_TIME_IN = "authorizedTimeIn"
        const val TIME_OUT = "timeOut"
        const val AUTHORIZED_TIME_OUT = "authorizedTimeOut"

    }
}