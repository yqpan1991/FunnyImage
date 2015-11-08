package com.edus.apollo.funny.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.edus.apollo.funny.utils.EsPackageUtils;
import com.edus.apollo.funny.utils.EsSharedPreference;

/**
 * Created by Panda on 2015/10/19.
 */
public class StartupActivity extends BaseActivity {
    public static final String EXTRA_INFO = "extra_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no ui
        //just handle logic startup

       /* 如果是第一次打开,或者是升级而来,那么显示向导页(显示向导页面时需要注册)

        否则显示欢迎页(也需要注册)

                如果有其他的intent,那么直接进入到mainActivity进行处理即可
*/
        Intent comingIntent = getIntent();
        Bundle bundle = comingIntent.getExtras();
        if (bundle != null) {
            //start up from share etc
            startMainActivity(bundle);
            finish();
        } else {
            //normal start up
            int version = EsSharedPreference.getVersion();
            int currentVersion = EsPackageUtils.getAppVersionCode(getApplicationContext());
            /*if (version < currentVersion) {
                startGuideActivity();
            } else {
                startWelcomeActivity();
            }*/
            startWelcomeActivity();
            finish();
        }
    }

    private void startMainActivity(Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void startGuideActivity() {
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        startActivity(intent);
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
    }


}
