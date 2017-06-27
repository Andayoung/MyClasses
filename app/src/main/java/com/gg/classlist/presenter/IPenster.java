package com.gg.classlist.presenter;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public interface IPenster {
    void postClasses(Object classes);

    void loadClasser(int id);

    void getClassList(String weekday);
}
