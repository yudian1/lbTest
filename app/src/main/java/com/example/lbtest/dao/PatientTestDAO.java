package com.example.lbtest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lbtest.model.PatientTest;

public class PatientTestDAO {
    private final static String TABLE_NAME02 = "test_table";
    private String PATIENT_ID = "id";
    private String PATIENT_DATE = "date";
    private String PATIENT_FIRST = "first";
    private String PATIENT_DBAS = "dbas";
    private String PATIENT_SHPS = "shps";
    private PatientDB helper;
    private SQLiteDatabase db;
    public PatientTestDAO(Context context){
        helper = new PatientDB(context);// 初始化DBOpenHelper对象
    }
    //增加病人测试数据
    public void insert(PatientTest patienttest){
        //创建一个可写的数据库
        db = helper.getWritableDatabase();
        //ContentValues以键值对的形式存放数据
        ContentValues cv = new ContentValues();
        cv.put(PATIENT_ID,patienttest.getId());
        cv.put(PATIENT_DATE,patienttest.getDate());
        cv.put(PATIENT_FIRST, patienttest.getFirst());
        cv.put(PATIENT_DBAS, patienttest.getDbas());
        cv.put(PATIENT_SHPS, patienttest.getShps());
        System.out.println("创建DBAS成功！！");
        db.insert(TABLE_NAME02, null, cv);
        //return row;

    }
    //修改病人信息
    public void updateFirst(int id ,PatientTest patienttest){
        //获得一个可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //修改条件
        String where = PATIENT_ID + " = ?";
        //修改条件的参数  Integer.toString(id)，把数字用字符串表示
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(PATIENT_FIRST,patienttest.getFirst());
        //System.out.println("失败了？？？？？");
        db.update(TABLE_NAME02, cv, where, whereValue);
    }
    //修改病人信息
    public void updateDbas(int id ,PatientTest patienttest){
        //获得一个可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //修改条件
        String where = PATIENT_ID + " = ?";
        //修改条件的参数  Integer.toString(id)，把数字用字符串表示
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(PATIENT_DATE, patienttest.getDate());
        cv.put(PATIENT_DBAS, patienttest.getDbas());
        db.update(TABLE_NAME02, cv, where, whereValue);
    }
    //修改病人信息
    public void updateShps(int id ,PatientTest patienttest){
        //获得一个可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //修改条件
        String where = PATIENT_ID + " = ?";
        //修改条件的参数  Integer.toString(id)，把数字用字符串表示
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(PATIENT_SHPS, patienttest.getShps());
        db.update(TABLE_NAME02, cv, where, whereValue);
    }

    //查找病人信息
    public PatientTest find(int id){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select id,date,first,dbas,shps from test_table where id = ?",
                new String[] {String.valueOf(id)});//根据编号查找到的病人信息，并存储到Cursor中
        //遍历查找到的病人信息
        if(cursor.moveToNext()){
            //将遍历查找到的病人信息存储到PatientInformation类中
            return new PatientTest(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("first")),
                    cursor.getString(cursor.getColumnIndex("dbas")),
                    cursor.getString(cursor.getColumnIndex("shps"))
            );
        }
        return null;
    }
    //删除数据
    public void delete(Integer... ids){
	/*db = helper.getWritableDatabase();
	String where = PATIENT_ID + " = ?";
	String[] whereValue ={ Integer.toString(id) };
	db.delete(TABLE_NAME, where, whereValue);*/
        if(ids.length > 0){ //判断是否存在要删除的id
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < ids.length;i++){
                sb.append('?').append(','); //将删除条件添加到StringBuffer中
            }
            sb.deleteCharAt(sb.length() - 1);
            db = helper.getWritableDatabase();
            db.execSQL("delete from patient where _id in (" + sb + ")",
                    (Object[]) ids);
        }
    }
    //得到数据库中的数据
    public Cursor export(){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery( "select * from test_table",null);
        return cursor;
    }
    //获取总记录数
    public long getCount() {

        db = helper.getWritableDatabase();
        Cursor cursor = db
                .rawQuery("select count(id) from patient_table", null);//获取病人信息记录数
        if (cursor.moveToNext())//判断Cursor中是否有数据
        {
            return cursor.getLong(0);//返回总记录数
        }
        return 0;//没有数据则返回0

    }
    //获取病人的最大编号
    public int getMaxId() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from patient_table", null);//获取病人信息中的最大编号
        //访问Cursor中的最后一条数据
        while (cursor.moveToLast()) {
            return cursor.getInt(0);//获取访问到的数据，即最大编号
        }
        return 0;//如果没有数据则返回0
    }

}

