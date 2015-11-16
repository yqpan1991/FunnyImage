package com.edus.apollo.funny.net.api;

/**
 * Created by Panda on 2015/10/26.
 */
public class EsApi {

    private static final String HOST = "http://mobile.bugua.com";
    public static final String REGISTER = "/sign_in";
    public static final String TEMPLATE = "/template";
    public static final String CLASSIFY = "/template/cls";

    public static final String PIC_HOST = "http://pic.bugua.com";


    public static String getFullUrl(String url){
        return HOST + url;
    }

    public static String getFullPicUrl(String picSimpleUrl){
        return PIC_HOST + "/"+picSimpleUrl;
    }



}
