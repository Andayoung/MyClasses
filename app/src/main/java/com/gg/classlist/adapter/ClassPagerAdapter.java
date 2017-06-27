package com.gg.classlist.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gg.classlist.view.ClassesView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ClassPagerAdapter extends PagerAdapter {
    private List<ClassesView> mViewList;
    private String[] mTitleList;

    public ClassPagerAdapter(List<ClassesView> mViewList, String[] mTitleList) {
        this.mViewList = mViewList;
        this.mTitleList=mTitleList;
    }
    public void setData(List<ClassesView> mViewList){
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];//页卡标题
    }
}
