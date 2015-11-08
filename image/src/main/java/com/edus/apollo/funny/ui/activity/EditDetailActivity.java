package com.edus.apollo.funny.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.api.EsApi;
import com.edus.apollo.funny.net.model.MakeModule;
import com.edus.apollo.funny.ui.EsApplication;
import com.edus.apollo.funny.ui.adapter.EditDetailAdapter;
import com.edus.apollo.funny.utils.EsFile;
import com.edus.apollo.funny.utils.EsViewUtils;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Panda on 2015/11/3.
 */
public class EditDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String EXTRA_TEMPLATE = "extra_template";
    private MakeModule.Template mTemplate;

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvShare;
    private ListView mLvText;
    private EditText mEtContent;

    private TextView mTvResult;
    private EditDetailAdapter mAdapter;

    private RelativeLayout mRlDisplay;
    private ImageView mIvImage;
    private TextView mTvText;
    private ProgressBar mPbProgress;

    private boolean isCalculated;
    private int mRlCalWidth;
    private int mRlCalHeight;

    private Handler uiHandler;

    private ImageView mIvCombine;

    private int mDownloadId = -1;



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
        mRlDisplay = (RelativeLayout) findViewById(R.id.rl_display);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mPbProgress = (ProgressBar) findViewById(R.id.pg_progress);
        mIvCombine = (ImageView) findViewById(R.id.iv_combine);

        mIvBack.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
        initData();
    }

    private void initData() {
        parseIntent();
        if(mTemplate == null){
            finish();
            return;
        }
        uiHandler = new Handler();
        mRlDisplay.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mRlCalWidth = mRlDisplay.getWidth();
                mRlCalHeight = mRlDisplay.getHeight();
                mRlDisplay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                isCalculated = true;
            }
        });

        mTvResult.setText(JSON.toJSONString(mTemplate));
        Log.e(TAG, JSON.toJSONString(mTemplate));
        mTvTitle.setText(mTemplate.title);
        mAdapter = new EditDetailAdapter(this);
        mLvText.setAdapter(mAdapter);
        mAdapter.setData(Arrays.asList(mTemplate.withText));
        mLvText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int adapterPosition = position - mLvText.getHeaderViewsCount();
                if (adapterPosition < 0 || adapterPosition >= mAdapter.getCount()) {
                    return;
                }
                String item = mAdapter.getItem(adapterPosition);
                mEtContent.setText(item);
            }
        });
        mEtContent.addTextChangedListener(mTextWatcher);
        showDownloadingView();
        loadData();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(s)){
                s = mTemplate.text;
            }
            mTvResult.setText(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loadData() {
        String name = mTemplate.pic;
        String imagePath = EsFile.getFullImagePath(getApplicationContext(), name);
        if(new File(imagePath).exists()){
            downloadFinish(imagePath);
        }else{
            downloadImage(imagePath);
        }

    }

    private void downloadFinish(String localPath) {
        if(isCalculated){
            if(resize(localPath)){
                showResultView();
            }
        }else{
            checkCalculated(20, localPath);
        }
    }

    private boolean  resize(String localPath) {
        //获取当前的图片信息
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(localPath,options);
        if(options.mCancel || options.outHeight == -1 || options.outWidth == -1){
            return false;
        }
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        //比较宽高比
        //如果bitmap的宽高比大于view的宽高比,降低view的高度
        //否则,减少view的宽度
        double imageRatio = imageWidth * 1.0 / imageHeight;
        if(imageRatio > mRlCalWidth * 1.0 / mRlCalHeight){
            mRlCalHeight = (int) Math.round(mRlCalWidth * 1.0 / imageRatio+0.5);
        }else{
            mRlCalWidth = (int) Math.round(mRlCalHeight * imageRatio+0.5);
        }
        ViewGroup.LayoutParams layoutParams = mRlDisplay.getLayoutParams();
        layoutParams.height = mRlCalHeight;
        layoutParams.width = mRlCalWidth;
        mRlDisplay.setLayoutParams(layoutParams);

        mIvImage.setImageBitmap(BitmapFactory.decodeFile(localPath));

        //2. 计算偏移量
        //3. 计算位置
        double widthRatio = mRlCalWidth * 1.0 / imageWidth;
        double heightRatio = mRlCalHeight * 1.0 / imageHeight;
        Rect rect = mTemplate.fetchRect();
        if(rect == null){
            return false;
        }
        int marginLeft = (int) (widthRatio * rect.left+0.5);
        int marginTop = (int) (heightRatio * rect.top+0.5);
        int tvWidth = (int) (widthRatio*rect.width()+0.5);
        int tvHeight = (int) (heightRatio*rect.height()+0.5);

        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) mTvResult.getLayoutParams();
        layoutParams1.topMargin = marginTop;
        layoutParams1.leftMargin = marginLeft;
        layoutParams1.width = tvWidth;
        layoutParams1.height = tvHeight;
        mTvResult.setLayoutParams(layoutParams1);
        mTvResult.setText(mTemplate.text);
        if(mTemplate.style == 2){
            mTvResult.setTextColor(getResources().getColor(R.color.color_white));
        }else{
            mTvResult.setTextColor(getResources().getColor(R.color.color_black));
        }

        return true;

    }


    private void checkCalculated(int delay, final String path){
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadFinish(path);
            }
        }, delay);
    };


    private void downloadImage(final String localPath) {
        ThinDownloadManager downloadMgr = EsApplication.getDownloadMgr();
        final DownloadRequest downloadRequest = new DownloadRequest(Uri.parse(EsApi.getFullPicUrl(mTemplate.pic)))
                .setDestinationURI(Uri.parse(localPath)).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        downloadFinish(localPath);
//                        Toast.makeText(EditDetailActivity.this, "下载成功~~~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(EditDetailActivity.this, "下载失败~~~", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {

                    }
                });
        mDownloadId = downloadMgr.add(downloadRequest);

    }



    private void showDownloadingView() {
        mPbProgress.setVisibility(View.VISIBLE);
        mRlDisplay.setVisibility(View.INVISIBLE);
    }

    private void showResultView(){
        mPbProgress.setVisibility(View.INVISIBLE);
        mRlDisplay.setVisibility(View.VISIBLE);
    }


    private void parseIntent() {
        Intent intent = getIntent();
        mTemplate = (MakeModule.Template) intent.getSerializableExtra(EXTRA_TEMPLATE);
    }


    public static Intent getEditDetailIntent(Context context, MakeModule.Template template){
        Intent intent = new Intent(context,EditDetailActivity.class);
        intent.putExtra(EXTRA_TEMPLATE, template);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EsApplication.getDownloadMgr().cancel(mDownloadId);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_share:
                handleTvShareClicked();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mIvCombine.getVisibility() == View.VISIBLE){
            mIvCombine.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }

    private void handleTvShareClicked() {
        mIvCombine.setVisibility(View.VISIBLE);
        mIvCombine.setImageBitmap(EsViewUtils.getViewCache(mRlDisplay));
    }
}
