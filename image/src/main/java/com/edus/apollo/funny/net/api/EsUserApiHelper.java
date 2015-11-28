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
import com.edus.apollo.funny.net.model.ClassifyDetailModule;
import com.edus.apollo.funny.net.model.ClassifyModule;
import com.edus.apollo.funny.net.model.MakeModule;
import com.edus.apollo.funny.ui.EsApplication;
import com.edus.apollo.funny.utils.EsDeviceUtils;
import com.edus.apollo.funny.volley.VolleySingleton;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2015/10/21.
 */
public class EsUserApiHelper {

    private static final String TAG = EsUserApiHelper.class.getSimpleName();

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

        JsonRequest<MakeModule> jsonRequest = new JsonRequest<MakeModule>(Request.Method.GET,EsApi.getFullUrl(EsApi.TEMPLATE),null,sucListener,errListener) {
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

    public static void getClassifyList(Response.Listener<ClassifyModule> sucListener,Response.ErrorListener errListener){
        JsonRequest<ClassifyModule> jsonRequest = new JsonRequest<ClassifyModule>(Request.Method.GET,EsApi.getFullUrl(EsApi.CLASSIFY),null,sucListener,errListener) {
            @Override
            protected Response<ClassifyModule> parseNetworkResponse(NetworkResponse networkResponse) {
                String parsed;
                ClassifyModule module;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                    EsLog.e(TAG, "getTemplateList:" + parsed);
                    module = JSON.parseObject(parsed,ClassifyModule.class);
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(networkResponse.data);
                    module = JSON.parseObject(parsed,ClassifyModule.class);
                }
                return Response.success(module, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return EsNetUtils.getCommonHeader(EsApplication.getContext());
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),jsonRequest);
    }

    /**
     * 获取到某个具体分类下的内容,包括tag,以及热门下的第一页数据
     * @param resId 分类id
     * @param sucListener
     * @param errListener
     */
    public static void getClassifyDetailList(String resId, Response.Listener<ClassifyDetailModule> sucListener,Response.ErrorListener errListener){
        JsonRequest<ClassifyDetailModule> jsonRequest = new JsonRequest<ClassifyDetailModule>(Request.Method.GET,EsApi.getFullUrl(EsApi.CLASSIFY_DETAIL,new Object[]{resId}),null,sucListener,errListener) {
            @Override
            protected Response<ClassifyDetailModule> parseNetworkResponse(NetworkResponse networkResponse) {
                String parsed;
                ClassifyDetailModule module;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                    EsLog.e(TAG, "getTemplateList:" + parsed);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(networkResponse.data);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                }
                return Response.success(module, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return EsNetUtils.getCommonHeader(EsApplication.getContext());
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),jsonRequest);
    }

    /**
     * 获取全部下的内容以及列表
     * @param resId 分类id
     * @param pageIndex 请求页面index
     * @param sequence 请求序列 0 热门 1 最新
     * @param sucListener
     * @param errListener
     */
    public static void getClassifyDetailPage(String resId,int pageIndex, int sequence, Response.Listener<ClassifyDetailModule> sucListener,Response.ErrorListener errListener){
        JsonRequest<ClassifyDetailModule> jsonRequest = new JsonRequest<ClassifyDetailModule>(Request.Method.GET,EsApi.getFullUrl(EsApi.CLASSIFY_DETAIL_PAGE,new Object[]{resId, pageIndex, sequence}),null,sucListener,errListener) {
            @Override
            protected Response<ClassifyDetailModule> parseNetworkResponse(NetworkResponse networkResponse) {
                String parsed;
                ClassifyDetailModule module;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                    EsLog.e(TAG, "getTemplateList:" + parsed);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(networkResponse.data);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                }
                return Response.success(module, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return EsNetUtils.getCommonHeader(EsApplication.getContext());
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),jsonRequest);
    }


    /**
     * 获取到符合tag的列表
     * @param resId
     * @param tagId
     * @param pageIndex
     * @param sequence 请求序列 0 热门 1 最新
     * @param sucListener
     * @param errListener
     */
    public static void getClassifyDetailListByTag(String resId, String tagId,int pageIndex,int sequence, Response.Listener<ClassifyDetailModule> sucListener,Response.ErrorListener errListener){
        JsonRequest<ClassifyDetailModule> jsonRequest = new JsonRequest<ClassifyDetailModule>(Request.Method.GET,EsApi.getFullUrl(EsApi.CLASSIFY_DETAIL_TAG,new Object[]{resId, tagId, pageIndex, sequence}),null,sucListener,errListener) {
            @Override
            protected Response<ClassifyDetailModule> parseNetworkResponse(NetworkResponse networkResponse) {
                String parsed;
                ClassifyDetailModule module;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                    EsLog.e(TAG, "getTemplateList:" + parsed);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(networkResponse.data);
                    module = JSON.parseObject(parsed,ClassifyDetailModule.class);
                }
                return Response.success(module, HttpHeaderParser.parseCacheHeaders(networkResponse));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return EsNetUtils.getCommonHeader(EsApplication.getContext());
            }
        };
        VolleySingleton.addRequest(EsApplication.getContext(),jsonRequest);
    }





}
