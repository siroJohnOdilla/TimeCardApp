package com.kronos.timecardapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper3 (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                LEAVE_TYPE + " TEXT, " +
                DATE_FROM + " TEXT, " +
                DATE_TO + " TEXT, " +
                NO_OF_DAYS + " TEXT, " +
                RELIEVER_NAME + " TEXT, " +
                AUTHORIZED_BY + " TEXT " + " )")

        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addDetails(name: String, leaveType: String, dateFrom: String, dateTo: String, noOfDays: String, reliever: String, authorizedBy: String ){
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(LEAVE_TYPE, leaveType)
        values.put(DATE_FROM, dateFrom)
        values.put(DATE_TO, dateTo)
        values.put(NO_OF_DAYS, noOfDays)
        values.put(RELIEVER_NAME, reliever)
        values.put(AUTHORIZED_BY, authorizedBy)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getDetails() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null,null)
    }

    companion object {
        private const val DATABASE_NAME = "LEAVE SCHEDULE DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "leave_schedule_table"
        const val ID_COL = "id"
        const val NAME_COL = "name"
        const val LEAVE_TYPE = "leaveType"
        const val DATE_FROM = "dateFrom"
        const val DATE_TO = "dateTo"
        const val NO_OF_DAYS = "noOfDays"
        const val RELIEVER_NAME = "relieverName"
        const val AUTHORIZED_BY = "authorizedBy"

    }
}