package com.edus.apollo.funny.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edus.apollo.funny.R;

/**
 * Created by Panda on 2015/9/20.
 */
public class MarkFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mark_fragment_layout,container,false);
    }
}
