package com.edus.apollo.funny.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.api.EsApi;
import com.edus.apollo.funny.net.model.ClassifyModule;
import com.edus.apollo.funny.net.model.MakeModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panda on 2015/11/9.
 */
public class ClassifyAdapter extends BaseExpandableListAdapter {

    public static final int VIEW_CLICK_TYPE_0 = 0;
    public static final int VIEW_CLICK_TYPE_1 = 1;
    public static final int VIEW_CLICK_TYPE_2 = 2;
    public static final int VIEW_CLICK_TYPE_3 = 3;

    private Context mContext;
    private LayoutInflater mInflater;
    private OnChildViewClickListener mListener;

    private List<ClassifyModule.GroupItem> mDataList = new ArrayList<>();


    public ClassifyAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<ClassifyModule.GroupItem> dataList) {
        mDataList.clear();
        if (dataList != null && !dataList.isEmpty()) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public void setOnChildClickListener(OnChildViewClickListener listener){
        mListener = listener;
    }

    public interface OnChildViewClickListener{
        void OnChildViewClick(View view, View rootView,int groupPosition,int childPosition,int subPosition);
    }

    @Override
    public int getGroupCount() {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataList.get(groupPosition).calRowCount();
    }

    @Override
    public ClassifyModule.GroupItem getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    @Override
    public ClassifyModule.SingleItem[] getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).fetchRowData(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        RowViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new RowViewHolder();
            convertView = mInflater.inflate(R.layout.classify_title_item, parent, false);
            convertView.setTag(viewHolder);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        }
        viewHolder = (RowViewHolder) convertView.getTag();
        viewHolder.tvTitle.setText(getGroup(groupPosition).name);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        RowViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new RowViewHolder();
            convertView = mInflater.inflate(R.layout.classify_normal_item, parent, false);
            convertView.setTag(viewHolder);
            viewHolder.subViewHolders = new SingleItemViewHolder[4];
            for (int index = 0; index < 4; index++) {
                viewHolder.subViewHolders[index] = new SingleItemViewHolder();
            }
            viewHolder.subViewHolders[0].rlLayout = convertView.findViewById(R.id.rl_0);
            viewHolder.subViewHolders[0].ivImage = (ImageView) convertView.findViewById(R.id.iv_0);
            viewHolder.subViewHolders[0].tvTitle = (TextView) convertView.findViewById(R.id.tv_0);

            viewHolder.subViewHolders[1].rlLayout = convertView.findViewById(R.id.rl_1);
            viewHolder.subViewHolders[1].ivImage = (ImageView) convertView.findViewById(R.id.iv_1);
            viewHolder.subViewHolders[1].tvTitle = (TextView) convertView.findViewById(R.id.tv_1);

            viewHolder.subViewHolders[2].rlLayout = convertView.findViewById(R.id.rl_2);
            viewHolder.subViewHolders[2].ivImage = (ImageView) convertView.findViewById(R.id.iv_2);
            viewHolder.subViewHolders[2].tvTitle = (TextView) convertView.findViewById(R.id.tv_2);

            viewHolder.subViewHolders[3].rlLayout = convertView.findViewById(R.id.rl_3);
            viewHolder.subViewHolders[3].ivImage = (ImageView) convertView.findViewById(R.id.iv_3);
            viewHolder.subViewHolders[3].tvTitle = (TextView) convertView.findViewById(R.id.tv_3);

        }
        viewHolder = (RowViewHolder) convertView.getTag();
        final View finalConvertView = convertView;
        ClassifyModule.SingleItem[] datas = getChild(groupPosition, childPosition);
        for (int index = 0; index < 4; index++) {
            final int finalIndex = index;
            if(datas != null && datas.length > index){
                viewHolder.subViewHolders[index].tvTitle.setText(datas[index].name);
                loadImage(datas[index], viewHolder.subViewHolders[index].ivImage);
                viewHolder.subViewHolders[index].rlLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListener != null){
                            mListener.OnChildViewClick(v, finalConvertView,childPosition,groupPosition, finalIndex);
                        }
                    }
                });
            }else{
                viewHolder.subViewHolders[index].tvTitle.setText(null);
                viewHolder.subViewHolders[index].ivImage.setImageBitmap(null);
                viewHolder.subViewHolders[index].rlLayout.setOnClickListener(null);
            }

        }

        return convertView;
    }

    private void loadImage(ClassifyModule.SingleItem singleItem, ImageView imageView) {
        Glide.with(mContext).load(EsApi.getFullPicUrl(singleItem.coverUrl)).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class RowViewHolder {
        public TextView tvTitle;
        public SingleItemViewHolder[] subViewHolders;
    }

    public static class SingleItemViewHolder {
        public View rlLayout;
        public TextView tvTitle;
        public ImageView ivImage;
    }

}
