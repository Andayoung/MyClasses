package com.gg.classlist.model;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public interface IModel {
    void loadList(String id, OnLoadListener onLoadListener);

    interface OnLoadListener {
        void onFailed(String error);

        void onSucceed(String list);
    }
}
