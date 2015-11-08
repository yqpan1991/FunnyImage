package com.edus.apollo.funny.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edus.apollo.funny.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panda on 2015/11/3.
 */
public class EditDetailAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<String> mDataList = new ArrayList<>();

    public EditDetailAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<String> list){
        mDataList.clear();
        if(list != null && !list.isEmpty()){
            mDataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public String getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.edit_detail_item,parent,false);
            viewHolder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        String item = getItem(position);
        viewHolder.mTvContent.setText(item);
        return convertView;
    }

    public static class ViewHolder{
        public TextView mTvContent;
    }
}
