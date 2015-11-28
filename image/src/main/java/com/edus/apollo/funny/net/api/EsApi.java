package com.edus.apollo.funny.net.api;

/**
 * Created by Panda on 2015/10/26.
 */
public class EsApi {

    private static final String HOST = "http://mobile.bugua.com";
    public static final String REGISTER = "/sign_in";
    public static final String TEMPLATE = "/template";
    public static final String CLASSIFY = "/template/cls";
    public static final String CLASSIFY_DETAIL = "/template/label/%1$s/home";

    public static final String CLASSIFY_DETAIL_PAGE = "/template/label/%1$s/content?page=%2$d&seq=%3$d";

    public static final String CLASSIFY_DETAIL_TAG = "/template/label/%1$s/home/%2$s?page=%3$d&seq=%4$d";


    public static final String PIC_HOST = "http://pic.bugua.com";


    public static String getFullUrl(String url){
        return HOST + url;
    }

    public static String getFullUrl(String url,Object[] params){
        return String.format(HOST+url,params);
    }

    public static String getFullPicUrl(String picSimpleUrl){
        return PIC_HOST + "/"+picSimpleUrl;
    }



}
