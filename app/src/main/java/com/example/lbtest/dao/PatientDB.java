package com.example.lbtest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PatientDB extends SQLiteOpenHelper{
    private final static String DATABASE_NAME = "patientdata.db";
    private final static int DATABASE_VERSION = 1;
    //创建的表格名
    private final static String TABLE_NAME02 = "test_table";

    //测试参数
    public final static String PATIENT_ID = "id";
    public final static String CURRENT_DATE = "date";
    public final static String FIRST = "first";
    public final static String DBAS = "dbas";
    public final static String SHPS = "shps";

    public PatientDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //创建表格
    @Override
    public void onCreate(SQLiteDatabase db) {

       //创建测试表
        String sql = "CREATE TABLE " + TABLE_NAME02 + " (" + PATIENT_ID + " integer primary key autoincrement, "  + CURRENT_DATE + " text, "+ FIRST + " text, " + DBAS + " text, "
                + SHPS +  " text);";
        Log.v("sql", sql);
        System.out.println("sql---" + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除旧版表格
        //String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        //db.execSQL(sql);
        //onCreate(db);
    }
}

