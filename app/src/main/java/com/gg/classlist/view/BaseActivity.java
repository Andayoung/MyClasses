package com.gg.classlist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/4 0004.
 */


public abstract class BaseActivity extends Activity {
    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initAllMembersView(savedInstanceState);
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

