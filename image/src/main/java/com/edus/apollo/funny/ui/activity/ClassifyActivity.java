package com.edus.apollo.funny.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.api.UserApiHelper;
import com.edus.apollo.funny.net.model.ClassifyModule;
import com.edus.apollo.funny.ui.adapter.ClassifyAdapter;
import com.edus.apollo.funny.utils.EsLog;

/**
 * Created by Panda on 2015/11/9.
 */
public class ClassifyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private SwipeRefreshLayout mSrlRefresh;
    private ExpandableListView mElvList;
    private ClassifyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        initView();
        initData();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mSrlRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        mSrlRefresh.setOnRefreshListener(mOnRefreshListener);
        mElvList = (ExpandableListView) findViewById(R.id.elv_list);
        mElvList.setOnGroupClickListener(mGroupClickListener);
        mIvBack.setOnClickListener(this);
    }

    private void expandAllGroup() {
        for(int index = 0; index< mAdapter.getGroupCount(); index++){
            mElvList.expandGroup(index);
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchData();
        }
    };

    private ExpandableListView.OnGroupClickListener mGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            return true;
        }
    };

    private void initData() {
        mAdapter = new ClassifyAdapter(this);
        mElvList.setAdapter(mAdapter);
        expandAllGroup();
        mSrlRefresh.setRefreshing(true);
        fetchData();
    }

    private void fetchData(){
        UserApiHelper.getClassifyList(new Response.Listener<ClassifyModule>() {
            @Override
            public void onResponse(ClassifyModule classifyModule) {
                EsLog.d(TAG, "onResponse:" + JSON.toJSONString(classifyModule));
                if(!classifyModule.isSuc()){
                    Toast.makeText(getApplicationContext(),"error:"+ classifyModule.getErrorMsg(),Toast.LENGTH_SHORT).show();
                }
                mAdapter.setData(classifyModule.groupItemList);
                expandAllGroup();
                mSrlRefresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                EsLog.d(TAG,"onError:"+ volleyError.toString());
                Toast.makeText(getApplicationContext(),"error:"+volleyError.toString(),Toast.LENGTH_SHORT).show();
                mSrlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
