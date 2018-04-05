package com.exceptionteam17.a101sexchallenges.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper{


    private static DatabaseHelper2 instance;

    private static final String DATABASE_NAME = "challengesSex101.db";
    private static int DATABASE_VERSION = 1;

    private static final String TABLE_USERS_SCORES = "users_scores";

    private static final String T_USERS_SCORES_COL_1 = "id";
    private static final String T_USERS_SCORES_COL_2 = "text";
    private static final String T_USERS_SCORES_COL_3 = "state";
    private static final String T_USERS_SCORES_COL_4 = "open_date";
    private static final String T_USERS_SCORES_COL_5 = "first_done";
    private static final String T_USERS_SCORES_COL_6 = "comment";


    private static final String USERS_SCORES_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS_SCORES +
            " (" + T_USERS_SCORES_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            T_USERS_SCORES_COL_2 + " TEXT, " +
            T_USERS_SCORES_COL_3 + " TEXT, " +
            T_USERS_SCORES_COL_4 + " INTEGER, " +
            T_USERS_SCORES_COL_5 + " INTEGER, " +
            T_USERS_SCORES_COL_6 + " TEXT);";



    private DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_SCORES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String drop = "DROP TABLE " + USERS_SCORES_TABLE_CREATE + ";";
//        db.execSQL(drop);
        onCreate(db);
    }

    public static synchronized DatabaseHelper2 getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper2(context.getApplicationContext());
        }
        return instance;
    }

///////////////////////////////////////////////
    public boolean addUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT " + T_USERS_SCORES_COL_1 + " FROM " + TABLE_USERS_SCORES + " WHERE " + T_USERS_SCORES_COL_1 + " = \"" + username + "\"";
        Cursor c = db.rawQuery(q, null);
        if (c.getCount() > 0) {
            c.close();
            return false;
        }
        c.close();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_USERS_SCORES_COL_1, username);
        contentValues.put(T_USERS_SCORES_COL_2, 0);
        contentValues.put(T_USERS_SCORES_COL_3, 0);
        contentValues.put(T_USERS_SCORES_COL_4, 0);
        contentValues.put(T_USERS_SCORES_COL_5, 0);
        contentValues.put(T_USERS_SCORES_COL_6, 0);

        long b = db.insert(TABLE_USERS_SCORES, null, contentValues);
        return (b != -1);
//        String query = "INSERT INTO " + TABLE_USERS_SCORES + "(" + T_USERS_SCORES_COL_1 + ") " + "VALUES(" + username + ")";
//        db.execSQL(query);
//        return true;

    }

    private Integer getUserStat(String username, Integer coll){
        SQLiteDatabase db = this.getWritableDatabase();
        String myRawQuery = "SELECT * FROM " + TABLE_USERS_SCORES + " WHERE " + T_USERS_SCORES_COL_1 + " = \"" + username + "\"";
        Cursor c = db.rawQuery(myRawQuery, null);
        if(c.getCount() == 0){
            return 0;
        }

        c.moveToFirst();
        Integer out = c.getInt(coll);
        c.close();
        return out;
    }


    private void addUserStat(String username, int coll){
        SQLiteDatabase db = this.getWritableDatabase();
        String coll_name = "";
        switch (coll){
            case 1:
                coll_name = T_USERS_SCORES_COL_2;
                break;
            case 2:
                coll_name = T_USERS_SCORES_COL_3;
                break;
            case 3:
                coll_name = T_USERS_SCORES_COL_4;
                break;
            case 4:
                coll_name = T_USERS_SCORES_COL_5;
                break;
            case 5:
                coll_name = T_USERS_SCORES_COL_6;
                break;
        }
        int i = getUserStat(username, coll) + 1;
        String update = "UPDATE " + TABLE_USERS_SCORES + " SET " + coll_name + " = " + i + " WHERE " + T_USERS_SCORES_COL_1 + " = \"" + username + "\"";
        db.execSQL(update);
    }
    /////////////////////////////////////////////

}
