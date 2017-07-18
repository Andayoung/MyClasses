package com.gg.classlist.view;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.gg.classlist.R;
import com.gg.classlist.SerialNumberHelper;
import com.gg.classlist.adapter.ClassPagerAdapter;
import com.gg.classlist.db.DBManager;
import com.gg.classlist.util.Classes;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

import static android.os.Environment.DIRECTORY_DOCUMENTS;


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
        mgr = new DBManager(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialNumberHelper serialNumberHelper = new SerialNumberHelper(this);
        String serialNumber = serialNumberHelper.read4File();
        if (serialNumber == null || serialNumber.equals("")) {
            //未登录，转向登录界面
            Log.e("MainActivity", "未登录");
            showDialog();
        } else {
            String[] s = serialNumber.split(" ");
            //登录，用serialNumber注册并绑定信鸽推送
            Context context = getApplicationContext();
            XGPushConfig.enableDebug(context, true);
            XGPushManager.registerPush(context, s[0], new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.e("TPush", "注册成功,Token值为：" + data);
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.e("TPush", "注册失败,错误码为：" + errCode + ",错误信息：" + msg);
                }
            });
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


    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("还没有登录？");
        builder.setTitle("提示");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("去登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("MainActivity", "未登录");
                dialog.dismiss();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        ComponentName comp = new ComponentName("com.gg.myinformation", "com.gg.myinformation.LogOrRegActivity");
                        intent.setComponent(comp);
                        intent.putExtra("appName", "habitTrain");
                        intent.setAction("intent.action.loginZ");
                        startActivity(intent);
                    }
                }, 1500);

            }
        });
        builder.create().show();
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
            ClassesView v = new ClassesView(this, String.valueOf(j));
            mViewList.add(v);
            tabs.addTab(tabs.newTab().setText(weeks[j]));
        }
        mAdapter = new ClassPagerAdapter(mViewList, weeks);
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
            if (arg1.getAction().equals(MainActivity.ACTION_INTENT_RECEIVER)) {

                for (int j = 0; j < 7; j++) {
                    mViewList.get(j).updateView(MainActivity.this);
                }
            }

        }
    }

}
