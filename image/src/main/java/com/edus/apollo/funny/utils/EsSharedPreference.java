package com.edus.apollo.funny.utils;

import com.edus.apollo.funny.ui.EsApplication;

/**
 * Created by Panda on 2015/10/19.
 */
public class EsSharedPreference {

    private static final String KEY_LAST_VERSION = "version";
    private static final String KEY_TICKET = "ticket";

    private static final String SETTING_TICKET_DEFAULT = null;
    private static final int SETTING_VERSION_DEFAULT = 0;

    public static void setTicket(String ticket){
        EsPreferencesUtils.putString(EsApplication.getContext(), KEY_TICKET, ticket);
    }

    public static String getTicket(){
        return EsPreferencesUtils.getString(EsApplication.getContext(), KEY_TICKET, SETTING_TICKET_DEFAULT);
    }


    public static void setVersion(int version){
        EsPreferencesUtils.putInt(EsApplication.getContext(), KEY_LAST_VERSION, version);
    }

    public static int getVersion(){
        return EsPreferencesUtils.getInt(EsApplication.getContext(), KEY_LAST_VERSION, SETTING_VERSION_DEFAULT);
    }

}
