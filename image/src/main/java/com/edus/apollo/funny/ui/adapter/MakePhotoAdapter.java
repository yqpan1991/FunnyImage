package com.edus.apollo.funny.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.model.ListGroup;
import com.edus.apollo.funny.net.api.EsApi;
import com.edus.apollo.funny.net.model.MakeModule;

import java.util.List;

/**
 * Created by Panda on 2015/10/16.
 */
public class MakePhotoAdapter extends BaseAdapter {

    private final String TAG = MakePhotoAdapter.this.getClass().getSimpleName();

    private static final int PER_SIZE = 3;

    private ListGroup mGroup = new ListGroup(PER_SIZE);

    private LayoutInflater mInflater;
    private Context mContext;
    private int screenWidth;
    private int screenHeight;

    private CommonItemClickListener mListener;

    public static final int CLICK_1_TEXT = 1;
    public static final int CLICK_2_TEXT = 2;
    public static final int CLICK_3_TEXT = 3;
    public static final int CLICK_1_IMAGE = 4;
    public static final int CLICK_2_IMAGE = 5;
    public static final int CLICK_3_IMAGE = 6;

    //数据需要进行划分来处理
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_NORMAL_ITEM = 2;
    public static final int TYPE_COUNT = 3;

    public MakePhotoAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    public void setItemClickListener(CommonItemClickListener listener){
        mListener = listener;
    }

    public void setData(List<MakeModule.Template> dataList) {
        mGroup.setData(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mGroup.getGroupCount() + 2;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null;
        } else if (position == 1) {
            return null;
        } else {
            return mGroup.getModelListByGroupPosition(position - 2);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_TITLE;
        } else {
            return TYPE_NORMAL_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_HEAD:
                return getHeaderView(position, convertView, parent);
            case TYPE_TITLE:
                return getTitleView(position, convertView, parent);
            case TYPE_NORMAL_ITEM:
                return getNormalItem(position, convertView, parent);
        }
        return null;
    }

    private View getNormalItem(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.template_normal_item, parent, false);
            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.height = screenWidth/3;
            convertView.setLayoutParams(layoutParams);
            viewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv_1);
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv_2);
            viewHolder.iv3 = (ImageView) convertView.findViewById(R.id.iv_3);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        MakeModule.Template[] templates = (MakeModule.Template[]) getItem(position);
        if(templates != null && templates.length == 3){
            loadImage(templates[0],viewHolder.iv1);
            loadImage(templates[1],viewHolder.iv2);
            loadImage(templates[2],viewHolder.iv3);

            viewHolder.iv1.setOnClickListener(new MyOnClickListener(convertView,CLICK_1_IMAGE,position));
            viewHolder.iv2.setOnClickListener(new MyOnClickListener(convertView,CLICK_2_IMAGE,position));
            viewHolder.iv3.setOnClickListener(new MyOnClickListener(convertView,CLICK_3_IMAGE,position));
        }
        return convertView;
    }

    private void loadImage(MakeModule.Template template, ImageView imageView) {
        Glide.with(mContext).load(EsApi.getFullPicUrl(template.thumb)).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    private View getTitleView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.template_title_item,parent,false);
        }
        return convertView;
    }

    private View getHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.template_head_item,parent,false);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_category);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_hot);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv_lastest);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tv1.setOnClickListener(new MyOnClickListener(convertView,CLICK_1_TEXT,position));
        viewHolder.tv2.setOnClickListener(new MyOnClickListener(convertView,CLICK_2_TEXT,position));
        viewHolder.tv3.setOnClickListener(new MyOnClickListener(convertView,CLICK_3_TEXT,position));
        return convertView;
    }

    private class MyOnClickListener implements View.OnClickListener{
        private View mRootView;
        private int mViewType;
        private int mAdapterPosition;

        public MyOnClickListener(View rootView,int viewType,int adapterPositon){
            mRootView = rootView;
            mViewType = viewType;
            mAdapterPosition = adapterPositon;
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemViewClicked(mAdapterPosition,mViewType,v,mRootView);
            }
        }
    }

    public static class ViewHolder {
        public ImageView iv1;
        public ImageView iv2;
        public ImageView iv3;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }


}
