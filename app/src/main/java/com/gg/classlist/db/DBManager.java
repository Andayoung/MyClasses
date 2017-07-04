package com.gg.classlist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gg.classlist.util.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class DBManager {
    private DBsqliteHelper helper;

    public DBManager(Context context) {
        helper = new DBsqliteHelper(context);
    }

    public void addAll(List<Classes> classes) {
        SQLiteDatabase db = helper.getWritableDatabase();
        for (Classes classz : classes) {
            db.execSQL("INSERT INTO myclass VALUES(null,?,?,?,?,?)", new Object[]{classz.classid, classz.starttime, classz.endtime, classz.name, classz.week});
        }
        Log.e("DBManager", "添加成功");
        db.close();

    }

    public void addForId(Classes classes) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO myclass VALUES(null,?,?,?,?,?)", new Object[]{classes.classid, classes.starttime, classes.endtime, classes.name, classes.week});
        Log.e("DBManager", "添加成功");
        db.close();

    }


    public void deleteForId(String classid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("myclass", "classid=?", new String[]{classid});
        Log.e("DBManager", "删除成功");
    }


    public List<Classes> queryForId(String classid) {
        ArrayList<Classes> classes = new ArrayList<Classes>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("myclass", null, "classid=?", new String[]{classid}, null, null, null, null);
        while (c.moveToNext()) {
            Classes classez = new Classes(c.getString(c.getColumnIndex("classid"))
                    , c.getString(c.getColumnIndex("starttime"))
                    , c.getString(c.getColumnIndex("endtime"))
                    , c.getString(c.getColumnIndex("name"))
                    , c.getString(c.getColumnIndex("week")));
            classes.add(classez);
        }
        c.close();
        return classes;
    }

    public List<Classes> queryForWeek(String week) {
        ArrayList<Classes> classes = new ArrayList<Classes>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("myclass", null, "week=?", new String[]{week}, null, null, "starttime  ASC", null);
        while (c.moveToNext()) {
            Classes classez = new Classes(c.getString(c.getColumnIndex("classid"))
                    , c.getString(c.getColumnIndex("starttime"))
                    , c.getString(c.getColumnIndex("endtime"))
                    , c.getString(c.getColumnIndex("name"))
                    , c.getString(c.getColumnIndex("week")));
            classes.add(classez);
        }
        c.close();
        return classes;
    }

    public List<Classes> query() {
        ArrayList<Classes> classes = new ArrayList<Classes>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM myclass", null);
        while (c.moveToNext()) {
            Classes classez = new Classes(c.getString(c.getColumnIndex("classid"))
                    , c.getString(c.getColumnIndex("starttime"))
                    , c.getString(c.getColumnIndex("endtime"))
                    , c.getString(c.getColumnIndex("name"))
                    , c.getString(c.getColumnIndex("week")));
            classes.add(classez);
        }
        c.close();
        return classes;
    }

}
