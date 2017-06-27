package com.gg.classlist.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.gg.classlist.util.ClassesCallBack;
import com.gg.classlist.view.MainActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ClassListReceiver extends XGPushBaseReceiver{

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
        Log.e("ClassListReceiver","title="+xgPushTextMessage.getTitle()+",content="+xgPushTextMessage.getContent()+",CustomContent="+xgPushTextMessage.getCustomContent());
        //刷新界面！
        Intent mIntent=new Intent(MainActivity.ACTION_INTENT_RECEIVER);
        mIntent.putExtra("customContent", xgPushTextMessage.getCustomContent());
        context.sendBroadcast(mIntent);
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        Log.e("ClassListReceiverx","title="+xgPushShowedResult.getTitle()+",content="+xgPushShowedResult.getContent());
    }


}
