package com.edus.apollo.funny.ui.manager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panda on 2015/9/20.
 */
public class NavigationBar {
    private Context mContext;
    private LayoutInflater mInflater;
    private RadioGroup mRadioGroup;
    private OnCheckedItemChangedListener mListener;

    private List<NavigationItem> mNavItemList = new ArrayList<>();

    public NavigationBar(Context context, RadioGroup radioGroup) {
        mContext = context;
        mRadioGroup = radioGroup;
        initData();
    }

    private void initData() {
        mInflater = LayoutInflater.from(mContext);
    }

    public void addItem(NavigationItem item) {
        mNavItemList.add(item);
        //inflate this item
        RadioButton radiobutton = new RadioButton(mContext);
        radiobutton.setText(item.mDescId);
        mRadioGroup.addView(radiobutton);
        item.resId = radiobutton.getId();
    }


    public void setOnCheckedChangedListener(OnCheckedItemChangedListener listener) {
        mListener = listener;
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mListener != null) {
                    for (NavigationItem item : mNavItemList) {
                        if (checkedId == item.resId) {
                            mListener.onCheckedItemChanged(item);
                            break;
                        }
                    }

                }

            }
        });
    }


    public interface OnCheckedItemChangedListener {
        void onCheckedItemChanged(NavigationItem item);
    }


    public void setCurrentItemIndex(int index) {
        if (index >= 0 && index < mNavItemList.size()) {
            mRadioGroup.check(mNavItemList.get(index).resId);
        }
    }


    public static class NavigationItem {
        private int mDescId;
        private int mDrawableId;
        private int resId;

        public NavigationItem(int descId, int drawableId) {
            mDescId = descId;
            mDrawableId = drawableId;
        }
    }


}
