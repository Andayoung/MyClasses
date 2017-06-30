package com.gg.classlist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gg.classlist.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class ClassesAdapter extends BaseAdapter {
    private List<Map<String, String>> mapList;
    private LayoutInflater mInflater = null;
    public  ClassesAdapter(Context context, List<Map<String, String>> mapList) {
        this.mapList = mapList;
        this.mInflater = LayoutInflater.from(context);
    }
    public void setData(List<Map<String, String>> mapList){
        this.mapList = mapList;
    }
    @Override
    public int getCount() {
        return mapList == null ? 0 : mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapList == null ? null : mapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mapList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.className = (TextView) convertView.findViewById(R.id.class_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#B3FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#B3FAFAFA"));
        }
        //{"id":"b576d878f7244df2933f8b97de5debda","endtime":"11:00","starttime":"09:00","page":1,"name":"语文课","serialnumber":"11","week":1,"rows":10},
        String startT=mapList.get(position).get("starttime");
        String endT=mapList.get(position).get("endtime");
        String classN=mapList.get(position).get("name");
        Log.e("ClassesAdapter","startT="+startT+",endT="+endT+",classN="+classN);
        holder.startTime.setText(startT);
        holder.endTime.setText(endT);
        holder.className.setText(classN);

        return convertView;
    }

    static class ViewHolder {
        public TextView startTime;
        public TextView endTime;
        public TextView className;
    }
}
