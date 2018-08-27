package com.example.lbtest.dao;

import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportToCSV {
    private String patient_name;
    public void setName(String name){
        patient_name = name;
        System.out.println("name---" + patient_name);
    }
    //导出表格的方法
    public void exportToCSV(Cursor c, String fileName) {

        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        //获取sd卡根目录
        File sdCardDir = Environment.getExternalStorageDirectory();

        //创建文件夹，要是有的话不创建,根据患者的姓名创建特定的姓名
        String filedir = sdCardDir +"/HKMedical_" + patient_name;
        //String filedir = sdCardDir +"/HKMedical";
        File file = new File(filedir);
        if(!file.exists()){
            try {
                //按照指定的路径创建文件夹
                file.mkdirs();
                System.out.println("创建HKMedical成功！");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }else{
            System.out.println("HKMedical文件夹已存在！");
        }
        //保存文件目录
        File saveFile = new File(filedir, fileName);
        try {

            rowCount = c.getCount();
            colCount = c.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                c.moveToFirst();
                // 写入表头
                for (int i = 0; i < colCount; i++) {
                    if (i != colCount - 1)
                        bfw.write(c.getColumnName(i) + ',');
                    else
                        bfw.write(c.getColumnName(i));
                }
                // 写好表头后换行
                bfw.newLine();
                // 写入数据
                for (int i = 0; i < rowCount; i++) {
                    c.moveToPosition(i);
                    // Toast.makeText(mContext, "正在导出第"+(i+1)+"条",
                    // Toast.LENGTH_SHORT).show();
                    Log.v("导出数据", "正在导出第" + (i + 1) + "条");
                    for (int j = 0; j < colCount; j++) {
                        if (j != colCount - 1)
                            bfw.write(c.getString(j) + ',');
                        else
                            bfw.write(c.getString(j));
                    }
                    // 写好每条记录后换行
                    bfw.newLine();
                }
            }
            // 将缓存数据写入文件
            bfw.flush();
            // 释放缓存
            bfw.close();
            // Toast.makeText(mContext, "导出完毕！", Toast.LENGTH_SHORT).show();
            Log.v("导出数据", "导出完毕！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            c.close();
        }
    }

}
