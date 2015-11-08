package com.edus.apollo.funny.net.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.edus.apollo.funny.net.model.BaseResponse;

/**
 * Created by Panda on 2015/10/27.
 */
public class RegisterResp extends BaseResponse {
    @JSONField(name="t")
    public String ticket;
}
