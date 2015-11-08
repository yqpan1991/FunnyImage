package com.edus.apollo.funny.net.api;

import android.content.Context;
import android.text.TextUtils;

import com.edus.apollo.funny.utils.EsSharedPreference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2015/11/1.
 */
public class EsNetUtils {

    private static Map<String,String> mHeader = null;

    public static synchronized Map<String,String> getCommonHeader(Context context){
        if(mHeader == null){
            mHeader = new HashMap<String,String>();
            mHeader.put(EsApiKey.HEADER_USER_AGENT,EsApiKey.HEADER_USER_AGENT_VALUE);
        }
        String ticket = EsSharedPreference.getTicket();
        if(!TextUtils.isEmpty(ticket)){
            mHeader.put(EsApiKey.KEY_TICKET,ticket);
        }
        return mHeader;
    }

}
