package com.edus.apollo.funny.net;


import com.alibaba.fastjson.annotation.JSONField;
import com.edus.apollo.funny.net.model.BaseNetworkEvent;

public class RtNetworkEvent extends BaseNetworkEvent {
    private boolean rt;
    @JSONField(name="message")
    private String rtMessage;

    public String getRtMessage() {
        return this.rtMessage;
    }

    public boolean isRt() {
        return this.rt;
    }

}
