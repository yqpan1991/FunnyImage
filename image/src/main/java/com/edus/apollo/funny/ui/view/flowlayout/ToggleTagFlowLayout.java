package com.edus.apollo.funny.ui.view.flowlayout;

import android.content.Context;
import android.util.AttributeSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Panda on 2015/11/28.
 */
public class ToggleTagFlowLayout extends TagFlowLayout {

    private int preSelectedPositon;


    public ToggleTagFlowLayout(Context context) {
        this(context, null);
    }

    public ToggleTagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mSupportMulSelected = true;
        mSelectedMax = 1;
        preSelectedPositon = -1;
    }

    public ToggleTagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public final void setMaxSelectCount(int count) {
        //so this method is not work
    }

    @Override
    protected void doSelect(TagView child, int position) {
        if (mSupportMulSelected) {
            boolean isChecked = child.isChecked();
            if (!isChecked) {
                child.setChecked(true);
                if (preCheckChild == null) {
                    preCheckChild = child;
                } else if (preCheckChild != child) {
                    preCheckChild.setChecked(false);
                    preCheckChild = child;
                }
                mSelectedView.clear();
                mSelectedView.add(position);
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelected(new HashSet<Integer>(mSelectedView), true);
                }
                if(mOnToggleSelectListener != null){
                    mOnToggleSelectListener.onSelected(preSelectedPositon,position);
                }
                preSelectedPositon = position;
            }
        }

    }

    private OnToggleSelectListener mOnToggleSelectListener;

    public void setOnToggleSelectListener(OnToggleSelectListener onToggleSelectListener){
        mOnToggleSelectListener = onToggleSelectListener;
    }

    public interface OnToggleSelectListener {
        void onSelected(int prePosition, int currentPosition);
    }
}
