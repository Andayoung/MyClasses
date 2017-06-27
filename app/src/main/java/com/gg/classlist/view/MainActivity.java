package com.gg.classlist.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.gg.classlist.R;
import com.gg.classlist.adapter.ClassPagerAdapter;
import com.gg.classlist.db.DBManager;
import com.gg.classlist.util.Classes;
import com.gg.classlist.util.SerialNumberHelper;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private DBManager mgr;
    private List<ClassesView> mViewList = new ArrayList<>();//页卡视图集合
    private String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    public MyBroadcastReceiver myReceiver;
    public static String ACTION_INTENT_RECEIVER = "com.gg.broadcast.receiver";
    private ClassPagerAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessageReceiver();
        SerialNumberHelper serialNumberHelper = new SerialNumberHelper(this);
        String serialNumber = serialNumberHelper.read4File();
        if (serialNumber == null || serialNumber.equals("")) {
            //未登录，转向登录界面
            Log.e("MainActivity","未登录");
            Intent intent = new Intent("intent.action.loginZ");
            intent.putExtra("appName","myclass");
            startActivity(intent);
        } else {
            //登录，用serialNumber注册并绑定信鸽推送
            Context context = getApplicationContext();
            XGPushConfig.enableDebug(context, true);
            XGPushManager.registerPush(context, serialNumberHelper.read4File(), new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.e("TPush", "注册成功,Token值为：" + data);
                }
                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.e("TPush", "注册失败,错误码为：" + errCode + ",错误信息：" + msg);
                }
            });
        }
        mgr = new DBManager(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if (clickedResult != null) {
            String title = clickedResult.getTitle();
            Log.e("TPush", "title:" + title);
            String id = clickedResult.getMsgId() + "";
            Log.e("TPush", "id:" + id);
            String content = clickedResult.getContent();
            Log.e("TPush", "content:" + content);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int j = 0; j < 7; j++) {
            ClassesView v = new ClassesView(this,String.valueOf(j));
            mViewList.add(v);
            tabs.addTab(tabs.newTab().setText(weeks[j]));
        }
        mAdapter = new ClassPagerAdapter(mViewList,weeks);
        vpView.setAdapter(mAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(mAdapter);

    }

    public void registerMessageReceiver() {
        myReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_INTENT_RECEIVER);
        registerReceiver(myReceiver, filter);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(ACTION_INTENT_RECEIVER)) {
                String content = arg1.getStringExtra("customContent");
                Log.e("onReceive", "content=" + content);
                List<Classes> classList=new ArrayList<>();
                try {
                    JSONObject jList=new JSONObject(content);
                    JSONArray classAll = jList.getJSONArray("list");
                    for (int i = 0; i < classAll.length(); i++) {
                        JSONObject classPer = classAll.getJSONObject(i);
                        Classes c=new Classes(classPer.getString("starttime"),classPer.getString("endtime")
                                ,classPer.getString("name"),classPer.getString("week"));
                        classList.add(c);
                        Log.e("MainActivity","starttime"+classPer.getString("starttime")+classPer.getString("endtime")
                                +classPer.getString("name")+classPer.getString("week"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mgr.deleteAll();
                mgr.addAll(classList);
                for (int j = 0; j < 7; j++) {
                    mViewList.get(j).updateView(MainActivity.this);
                }
            }

        }
    }

}
