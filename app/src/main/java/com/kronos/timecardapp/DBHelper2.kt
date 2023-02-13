package com.kronos.timecardapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper2 (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                DATE_COL + " TEXT, " +
                NAME_COL + " TEXT, " +
                JOB_TITLE_COL + " TEXT, " +
                DEPARTMENT_COL + " TEXT, " +
                OFFICE_SITE_COL + " TEXT, " +
                TIME_IN_COL + " TEXT, " +
                TIME_OUT_COL + " TEXT, " +
                TOTAL_TIME_WORKED_COL + " TEXT " + " )")

        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addDetails( date:String,
                    name: String,
                    jobTitle: String,
                    department: String,
                    officeBranchSite: String,
                    timeIn: String,
                    timeOut: String,
                    totalTimeWorked: String){
        val values = ContentValues()
        values.put(DATE_COL,date)
        values.put(NAME_COL,name)
        values.put(JOB_TITLE_COL,jobTitle)
        values.put(DEPARTMENT_COL,department)
        values.put(OFFICE_SITE_COL,officeBranchSite)
        values.put(TIME_IN_COL,timeIn)
        values.put(TIME_OUT_COL,timeOut)
        values.put(TOTAL_TIME_WORKED_COL,totalTimeWorked)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateDetails(id: Long, date1: String, name1: String, jobTitle1: String, department1: String , officeBranchSite1: String, timeIn1: String, timeOut1: String, totalTimeWorked1: String): Int{
        val values = ContentValues()
        values.put(DATE_COL,date1)
        values.put(NAME_COL,name1)
        values.put(JOB_TITLE_COL,jobTitle1)
        values.put(DEPARTMENT_COL,department1)
        values.put(OFFICE_SITE_COL,officeBranchSite1)
        values.put(TIME_IN_COL,timeIn1)
        values.put(TIME_OUT_COL,timeOut1)
        values.put(TOTAL_TIME_WORKED_COL,totalTimeWorked1)
        val whereclause = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclause, whereargs)
    }
    /*fun updateDetailsClockIn(id: Long, timeIn1: String): Int{
        val values = ContentValues()
        values.put(TIME_IN_COL,timeIn1)
        val whereclause = "$ID_COL=?"
        val whereargs = arrayOf(id.toString())
        return this.writableDatabase.update(TABLE_NAME, values, whereclause, whereargs)
    }*/

    fun getDetails() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null,null)
    }
    /*fun deleteDuplicate(name1: String){
        val whereclause = "$NAME_COL=?"
        val whereargs = arrayOf(name1)
        val db = this.writableDatabase
        db.delete(TABLE_NAME, whereclause, whereargs)
        db.close()
    }*/
    companion object {
        private const val DATABASE_NAME = "TIME ATTENDANCE DATABASE"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "time_attendance_table"
        const val DATE_COL = "date"
        const val ID_COL = "id"
        const val NAME_COL = "name"
        const val JOB_TITLE_COL = "jobTitle"
        const val DEPARTMENT_COL = "department"
        const val OFFICE_SITE_COL = "officeSiteBranch"
        const val TIME_IN_COL = "timeIn"
        const val TIME_OUT_COL = "timeOut"
        const val TOTAL_TIME_WORKED_COL = "totalTimeWorked"

    }
}
