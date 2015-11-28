package com.edus.apollo.funny.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.model.ClassifyDetailModule;
import com.edus.apollo.funny.ui.view.flowlayout.FlowLayout;
import com.edus.apollo.funny.ui.view.flowlayout.TagAdapter;

/**
 * Created by Panda on 2015/11/28.
 */
public class ClassifyTagAdapter extends TagAdapter<ClassifyDetailModule.TagItem> {

    private Context mContext;
    private LayoutInflater mInflater;

    public ClassifyTagAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, ClassifyDetailModule.TagItem singleItem) {
        View rootView = mInflater.inflate(R.layout.classify_tag_item,parent,false);
        TextView textView = (TextView) rootView.findViewById(R.id.tv_tag);
        textView.setText(singleItem.name);
        return rootView;
    }
}
