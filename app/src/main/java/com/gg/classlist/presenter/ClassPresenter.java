package com.gg.classlist.presenter;

import com.gg.classlist.model.ClassModel;
import com.gg.classlist.model.IModel;
import com.gg.classlist.model.IModel.OnLoadListener;
import com.gg.classlist.view.IView;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class ClassPresenter implements IPenster{
    private IModel mModel;
    private IView mView;
    public ClassPresenter(IView view) {
       mView=view;
        mModel = new ClassModel();
    }

    @Override
    public void postClasses(Object classes) {

    }

    @Override
    public void loadClasser(int id) {

    }

    @Override
    public void getClassList(String weekday) {
        mModel.loadList(weekday,mOnLoadListener);
    }
    private OnLoadListener mOnLoadListener = new OnLoadListener() {
        @Override
        public void onFailed(String error) {
            mView.showErrMessage(error);

        }

        @Override
        public void onSucceed(String yes) {
            mView.showClasser(yes);
        }

    };

}
