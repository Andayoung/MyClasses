package com.gg.classlist.util;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class Classes {
    public String classid;
    public String starttime;
    public String endtime;
    public String name;
    public String week;
    public  Classes(String classid,String starttime,String endtime,String name,String week){
        this.classid=classid;
        this.starttime=starttime;
        this.endtime=endtime;
        this.name=name;
        this.week=week;
    }
}
