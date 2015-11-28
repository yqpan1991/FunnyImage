package com.edus.apollo.funny.ui.view.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TagAdapter<T>
{
    private List<T> mTagDatas = new ArrayList<>();

    private OnDataChangedListener mOnDataChangedListener;

    public TagAdapter()
    {
    }

    public void setData(List<T> tagList){
        mTagDatas.clear();
        if(tagList != null && !tagList.isEmpty()){
            mTagDatas.addAll(tagList);
        }
        notifyDataChanged();
    }

    public List<T> getTagList(){
        return new ArrayList<>(mTagDatas);
    }

    static interface OnDataChangedListener
    {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener)
    {
        mOnDataChangedListener = listener;
    }

    public int getCount()
    {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged()
    {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position)
    {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);

}