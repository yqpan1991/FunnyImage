package com.edus.apollo.funny.ui;

import android.app.Application;
import android.content.Context;
import android.text.style.TtsSpan;

/**
 * Created by Panda on 2015/10/19.
 */
public class EsApplication extends Application {

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
