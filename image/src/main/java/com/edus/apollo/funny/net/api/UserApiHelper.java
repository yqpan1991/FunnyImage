package com.edus.apollo.funny.net.api;


import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.edus.apollo.funny.net.EsDeviceType;
import com.edus.apollo.funny.net.model.MakeModule;
import com.edus.apollo.funny.ui.EsApplication;
import com.edus.apollo.funny.utils.EsDeviceUtils;
import com.edus.apollo.funny.utils.EsLog;
import com.edus.apollo.funny.volley.VolleySingleton;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2015/10/21.
 */
public class UserApiHelper {

    private static final String TAG = UserApiHelper.class.getSimpleName();

    public static void registerUser(Response.Listener<String> sucListener,Response.ErrorListener errListener){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.REGISTER),sucListener,errListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKey.KEY_TP, EsDeviceType.TYPE_ANDROID);
                map.put(EsApiKey.KEY_DEVICE_ID,EsDeviceUtils.getEsDeviceId(EsApplication.getContext()));
                return map;
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),stringRequest);
    }

    public static void getTemplateList(Response.Listener<MakeModule> sucListener, Response.ErrorListener errListener){

        JsonRequest<MakeModule> jsonRequest = new JsonRequest<MakeModule>(Request.Method.POST,EsApi.getFullUrl(EsApi.TEMPLATE),null,sucListener,errListener) {
            @Override
            protected Response<MakeModule> parseNetworkResponse(NetworkResponse networkResponse) {
                String parsed;
                MakeModule makeModule = null;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                    EsLog.e(TAG, "getTemplateList:" + parsed);
                    makeModule = JSON.parseObject(parsed,MakeModule.class);
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(networkResponse.data);
                    makeModule = JSON.parseObject(parsed,MakeModule.class);
                }
                return Response.success(makeModule, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return EsNetUtils.getCommonHeader(EsApplication.getContext());
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),jsonRequest);
    }



}
