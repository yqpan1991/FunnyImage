package com.edus.apollo.funny.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Panda on 2015/10/16.
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;

    private Context mContext;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    private static VolleySingleton getInstance(Context context){
        if(mInstance == null){
            synchronized (VolleySingleton.class){
                if(mInstance == null){
                    mInstance = new VolleySingleton(context);
                }
            }
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static void addRequest(Context context,Request request){
        getInstance(context).getRequestQueue().add(request);
    }


}
