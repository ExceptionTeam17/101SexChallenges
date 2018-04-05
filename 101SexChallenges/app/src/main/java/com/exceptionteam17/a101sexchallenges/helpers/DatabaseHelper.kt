package com.exceptionteam17.a101sexchallenges.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.exceptionteam17.a101sexchallenges.model.Challenge

class DatabaseHelper private constructor(context: Context):
        SQLiteOpenHelper(context, "challengesSex101.db", null, 1) {

    companion object {

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    private val TABLE_USERS_SCORES = "users_scores"

    private val T_USERS_SCORES_COL_1 = "id"
    private val T_USERS_SCORES_COL_2 = "text"
    private val T_USERS_SCORES_COL_3 = "state"
    private val T_USERS_SCORES_COL_4 = "open_date"
    private val T_USERS_SCORES_COL_5 = "first_done"
    private val T_USERS_SCORES_COL_6 = "comment"


    private val USERS_SCORES_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS_SCORES +
            " (" + T_USERS_SCORES_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            T_USERS_SCORES_COL_2 + " TEXT, " +
            T_USERS_SCORES_COL_3 + " INTEGER, " +
            T_USERS_SCORES_COL_4 + " INTEGER, " +
            T_USERS_SCORES_COL_5 + " INTEGER, " +
            T_USERS_SCORES_COL_6 + " TEXT);"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(USERS_SCORES_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //        String drop = "DROP TABLE " + USERS_SCORES_TABLE_CREATE + ";";
        //        db.execSQL(drop);
        onCreate(db)
    }

    fun addOnFirstRun() {
        val db = this.writableDatabase
        val data = listOf<String>("test1", "Test2", "Test3")
        val contentValues = ContentValues()

        for(text: String in data){
            contentValues.put(T_USERS_SCORES_COL_2, text)
            contentValues.put(T_USERS_SCORES_COL_3, 1)
            contentValues.put(T_USERS_SCORES_COL_4, 0)
            contentValues.put(T_USERS_SCORES_COL_5, 0)
            contentValues.put(T_USERS_SCORES_COL_6, "")
            db.insert(TABLE_USERS_SCORES, null, contentValues);
            contentValues.clear()
        }
    }

    fun getData() : ArrayList<Challenge>{
        var data : ArrayList<Challenge> = ArrayList()
        val db = this.writableDatabase
        val myRawQuery = "SELECT * FROM $TABLE_USERS_SCORES"
        val c = db.rawQuery(myRawQuery, null)
        if (c.count == 0) {
            return data
        }

        c.moveToFirst()
        do{
            data.add(Challenge(c.getInt(0), c.getString(1), c.getInt(2),
                    c.getLong(3), c.getLong(4), c.getString(5)))
        } while(c.moveToNext())
        c.close()
        return data
    }
}