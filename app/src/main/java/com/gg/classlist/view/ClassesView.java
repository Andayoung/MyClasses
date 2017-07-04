package com.gg.classlist.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gg.classlist.R;
import com.gg.classlist.adapter.ClassesAdapter;
import com.gg.classlist.db.DBManager;
import com.gg.classlist.util.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class ClassesView extends RelativeLayout{
    private ClassesAdapter classesAdapter;
    private ListView listView;
    private List<Map<String, String>> mapList;
    private String week;
    private DBManager mgr;
    public ClassesView(Context context,String week) {
        super(context);
        this.week=week;
        LayoutInflater.from(context).inflate(R.layout.classes_layout, this);
        listView=(ListView) findViewById(R.id.ls_classes);
        mapList=new ArrayList<>();
        mgr = new DBManager(context);
        List<Classes> cz=mgr.queryForWeek(String.valueOf(Integer.valueOf(week)+1));
        Log.e("ClassesView","size="+cz.size());
        for(int i=0;i<cz.size();i++){
            Map<String,String> zp=new HashMap<>();
            zp.put("starttime",cz.get(i).starttime);
            zp.put("endtime",cz.get(i).endtime);
            zp.put("name",cz.get(i).name);
            zp.put("week",cz.get(i).week);
            Log.e("ClassesView","starttime="+cz.get(i).starttime+",endtime="+cz.get(i).endtime+
                    ",name="+cz.get(i).name+",week="+cz.get(i).week);
            mapList.add(zp);
        }
        classesAdapter=new ClassesAdapter(context,mapList);
        listView.setAdapter(classesAdapter);
    }
    public void updateView(Context context){
        Log.e("ClassesView","updateView");
        List<Map<String, String>>  listZ=new ArrayList<>();
        listZ.clear();
        mgr = new DBManager(context);
        List<Classes> cz=mgr.queryForWeek(String.valueOf(Integer.valueOf(week)+1));
        Log.e("ClassesView","size="+cz.size());
        for(int i=0;i<cz.size();i++){
            Map<String,String> zp=new HashMap<>();
            zp.put("starttime",cz.get(i).starttime);
            zp.put("endtime",cz.get(i).endtime);
            zp.put("name",cz.get(i).name);
            zp.put("week",cz.get(i).week);
            Log.e("ClassesView","starttime="+cz.get(i).starttime+",endtime="+cz.get(i).endtime+
                    ",name="+cz.get(i).name+",week="+cz.get(i).week);
            listZ.add(zp);
        }
        classesAdapter.setData(listZ);
        classesAdapter.notifyDataSetChanged();

    }
}
