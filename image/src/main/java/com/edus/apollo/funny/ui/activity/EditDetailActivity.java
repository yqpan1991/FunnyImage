package com.edus.apollo.funny.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.model.MakeModule;
import com.edus.apollo.funny.ui.adapter.EditDetailAdapter;

import java.util.Arrays;

/**
 * Created by Panda on 2015/11/3.
 */
public class EditDetailActivity extends BaseActivity {
    private static final String EXTRA_TEMPLATE = "extra_template";
    private MakeModule.Template mTemplate;

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvShare;
    private ListView mLvText;
    private EditText mEtContent;

    private TextView mTvResult;
    private EditDetailAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvShare = (TextView) findViewById(R.id.tv_share);
        mLvText = (ListView) findViewById(R.id.lv_text);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        initData();
    }

    private void initData() {
        parseIntent();
        if(mTemplate == null){
            finish();
        }
        mTvResult.setText(JSON.toJSONString(mTemplate));
        mTvTitle.setText(mTemplate.title);
        mAdapter = new EditDetailAdapter(this);
        mLvText.setAdapter(mAdapter);
        mAdapter.setData(Arrays.asList(mTemplate.withText));
        mLvText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int adapterPosition = position - mLvText.getHeaderViewsCount();
                if(adapterPosition < 0 || adapterPosition >= mAdapter.getCount()){
                    return;
                }
                String item = mAdapter.getItem(adapterPosition);
                mEtContent.setText(item);
            }
        });


    }

    private void parseIntent() {
        Intent intent = getIntent();
        mTemplate = (MakeModule.Template) intent.getSerializableExtra(EXTRA_TEMPLATE);
    }


    public static Intent getEditDetailIntent(Context context, MakeModule.Template template){
        Intent intent = new Intent(context,EditDetailActivity.class);
        intent.putExtra(EXTRA_TEMPLATE,template);
        return intent;
    }


}
