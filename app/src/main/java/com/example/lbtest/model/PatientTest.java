package com.example.lbtest.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class PatientTest implements KvmSerializable{
        int id;
        String date;
        String first;
        String dbas;
        String shps;

        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public PatientTest() {
            super();
        }
        public PatientTest(int id, String date, String first, String dbas,
                           String shps) {
            super();
            this.id = id;
            this.first = first;
            this.dbas = dbas;
            this.date = date;
            this.shps = shps;
        }
        public String getFirst() {
            return first;
        }
        public void setFirst(String first) {
            this.first = first;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getDbas() {
            return dbas;
        }
        public void setDbas(String dbas) {
            this.dbas = dbas;
        }
        public String getShps() {
            return shps;
        }
        public void setShps(String shps) {
            this.shps = shps;
        }

        @Override
        public Object getProperty(int index) {
            // TODO Auto-generated method stub
            Object obj = null;
            switch (index) {
                case 0:
                    obj = this.id;
                    break;
                case 1:
                    obj = this.date;
                    break;
                case 2 :
                    obj = this.first;
                    break;
                case 3:
                    obj = this.dbas;
                    break;
                case 4:
                    obj = this.shps;
                    break;
                default:
                    break;
            }
            return obj;
        }
        @Override
        public int getPropertyCount() {
            // TODO Auto-generated method stub
            return 5;
        }
        @Override
        public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
            // TODO Auto-generated method stub
            switch (index) {
                case 0:
                    info.name = "id";
                    info.type = PropertyInfo.INTEGER_CLASS;
                    break;
                case 1:
                    info.name = "date";
                    info.type = PropertyInfo.STRING_CLASS;
                    break;
                case 2:
                    info.name = "first";
                    info.type = PropertyInfo.STRING_CLASS;
                    break;
                case 3:
                    info.name = "dbas";
                    info.type = PropertyInfo.STRING_CLASS;
                    break;
                case 4:
                    info.name = "shps";
                    info.type = PropertyInfo.STRING_CLASS;
                    break;
                default:
                    break;
            }
        }
        @Override
        public void setProperty(int index, Object obj) {
            // TODO Auto-generated method stub
            switch (index) {
                case 0:
                    this.id = Integer.parseInt(obj.toString());
                    break;
                case 1:
                    this.date = obj.toString();
                    break;
                case 2:
                    this.first = obj.toString();
                    break;
                case 3:
                    this.dbas = obj.toString();
                    break;
                case 4:
                    this.shps= obj.toString();
                    break;

                default:
                    break;
            }
        }

    }

