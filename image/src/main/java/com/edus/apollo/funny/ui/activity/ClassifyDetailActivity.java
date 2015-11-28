package com.edus.apollo.funny.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.api.EsUserApiHelper;
import com.edus.apollo.funny.net.model.ClassifyDetailModule;
import com.edus.apollo.funny.net.model.ClassifyModule;
import com.edus.apollo.funny.ui.adapter.ClassifyTagAdapter;
import com.edus.apollo.funny.ui.view.flowlayout.ToggleTagFlowLayout;
import com.edus.apollo.funny.utils.EsLog;

/**
 * Created by Panda on 2015/11/16.
 */
public class ClassifyDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String EXTRA_SINGLE_ITEM = "extra_single_item";

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ToggleTagFlowLayout mTflLayout;
    private TextView mTvHot;
    private TextView mTvLatest;
    private ListView mLvList;
    private ClassifyTagAdapter mTagAdapter;

//    private SwipeRefreshLayout mSrlRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_detail);
        initView();
        initData();
    }



    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTflLayout = (ToggleTagFlowLayout) findViewById(R.id.fl_tag);
        mTflLayout.setOnToggleSelectListener(mToggleSelectListener);
        mTvHot = (TextView) findViewById(R.id.tv_hot);
        mTvHot.setOnClickListener(this);
        mTvLatest = (TextView) findViewById(R.id.tv_latest);
        mTvLatest.setOnClickListener(this);
//        mSrlRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
//        mSrlRefresh.setOnRefreshListener(mRefreshListener);
        mLvList = (ListView) findViewById(R.id.lv_list);
    }

    private void initData() {
        Intent intent = getIntent();
        ClassifyModule.SingleItem singleItem = (ClassifyModule.SingleItem) intent.getSerializableExtra(EXTRA_SINGLE_ITEM);
        if(singleItem == null){
            Toast.makeText(getApplicationContext(),"数据有误",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        EsLog.e(TAG, JSON.toJSONString(singleItem));
        mTvTitle.setText(singleItem.name);
        mTagAdapter = new ClassifyTagAdapter(this);
        mTflLayout.setAdapter(mTagAdapter);

        EsUserApiHelper.getClassifyDetailList(singleItem.id + "", new Response.Listener<ClassifyDetailModule>() {
            @Override
            public void onResponse(ClassifyDetailModule classifyDetailModule) {
                EsLog.e(TAG, "classifyDetailList:" + JSON.toJSONString(classifyDetailModule));
                //1. show tag
                if(classifyDetailModule.tagList != null){
                    ClassifyDetailModule.TagItem tagItem = new ClassifyDetailModule.TagItem();
                    tagItem.id = 0;
                    tagItem.name="全部";
                    classifyDetailModule.tagList.add(0, tagItem);
                }
                mTagAdapter.setData(classifyDetailModule.tagList);
                //2. show init list
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                EsLog.e(TAG, "error:" + volleyError.toString());
                mTagAdapter.setData(null);
            }
        });

        EsUserApiHelper.getClassifyDetailPage(singleItem.id+"",0,1,new Response.Listener<ClassifyDetailModule>(){

            @Override
            public void onResponse(ClassifyDetailModule classifyDetailModule) {
                EsLog.e(TAG, "getClassifyDetailPage:" + JSON.toJSONString(classifyDetailModule));
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                EsLog.e(TAG, "error:" + volleyError.toString());
            }
        });
    }

    private ToggleTagFlowLayout.OnToggleSelectListener mToggleSelectListener = new ToggleTagFlowLayout.OnToggleSelectListener() {
        @Override
        public void onSelected(int prePosition, int currentPosition) {
            EsLog.e(TAG,"prePosition:"+prePosition+",currentPosition:"+currentPosition);
        }
    };



    public static Intent getClassifyDetailActivityIntent(Context context, ClassifyModule.SingleItem singleItem){
        return new Intent(context.getApplicationContext(),ClassifyDetailActivity.class).putExtra(EXTRA_SINGLE_ITEM,singleItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_hot:
                handleHotClicked();
                break;
            case R.id.tv_latest:
                handleLatestClicked();
                break;

        }
    }

    private void handleHotClicked() {

    }

    private void handleLatestClicked() {

    }
}
