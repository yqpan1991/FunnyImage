package com.edus.apollo.funny.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.edus.apollo.funny.R;

/**
 * Created by Panda on 2015/10/20.
 */
public class GuideActivity extends BaseActivity {
    private Handler mUiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mUiHandler = new Handler();
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
                GuideActivity.this.finish();
            }
        },3000);

    }

    private void toMainActivity() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
