package com.gg.classlist.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.gg.classlist.db.DBManager;
import com.gg.classlist.util.Classes;
import com.gg.classlist.util.ClassesCallBack;
import com.gg.classlist.view.MainActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ClassListReceiver extends XGPushBaseReceiver {
    private DBManager mgr;

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
    }

    @Override
    public void onUnregisterResult(Context context, int i) {
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.e("ClassListReceiver", "title=" + xgPushTextMessage.getTitle() + ",content=" + xgPushTextMessage.getContent() + ",CustomContent=" + xgPushTextMessage.getCustomContent());
        //刷新界面！
        if(mgr==null){
            mgr = new DBManager(context);
        }
        String content = xgPushTextMessage.getCustomContent();
        Log.e("onReceive", "content=" + content);
        try {
            JSONObject jClass = new JSONObject(content);
            String type = jClass.getString("type");
            JSONObject model = jClass.getJSONObject("model");
            Classes c = new Classes(model.getString("id"), model.getString("starttime"), model.getString("endtime")
                    , model.getString("name"), model.getString("week"));
            if (type.equals("0")) {
                mgr.deleteForId(model.getString("id"));
            } else if (type.equals("1")) {
                mgr.addForId(c);
            } else if (type.equals("2")) {
                mgr.deleteForId(model.getString("id"));
                mgr.addForId(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent mIntent = new Intent(MainActivity.ACTION_INTENT_RECEIVER);
        mIntent.putExtra("customContent", xgPushTextMessage.getCustomContent());
        context.sendBroadcast(mIntent);
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        Log.e("ClassListReceiverx", "title=" + xgPushShowedResult.getTitle() + ",content=" + xgPushShowedResult.getContent());
    }


}
