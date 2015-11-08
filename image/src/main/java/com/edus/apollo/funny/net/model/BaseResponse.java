package com.edus.apollo.funny.net.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Panda on 2015/10/27.
 */
public class BaseResponse {

    @JSONField(name="rt")
    public boolean returnVal;

    @JSONField(name="message")
    public String message;

    public boolean isSuc(){
        return returnVal;
    }

    public String getErrorMsg(){
        return message;
    }

}
