package com.edus.apollo.funny.ui;

import android.app.Application;
import android.content.Context;
import android.text.style.TtsSpan;

import com.thin.downloadmanager.ThinDownloadManager;

/**
 * Created by Panda on 2015/10/19.
 */
public class EsApplication extends Application {

    private static Context mContext;

    private static ThinDownloadManager mDownloadMgr;

    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;

    public static Context getContext(){
        return mContext;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static ThinDownloadManager getDownloadMgr(){
        if(mDownloadMgr == null){
            mDownloadMgr = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        }
        return mDownloadMgr;
    }

    public static void destroy(){
        if(mDownloadMgr != null) mDownloadMgr.release();
    }
}
