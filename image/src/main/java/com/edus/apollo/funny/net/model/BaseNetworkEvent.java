package com.edus.apollo.funny.net.model;

import android.support.annotation.Nullable;

public class BaseNetworkEvent{
    private transient String message;

    public BaseNetworkEvent() {
        this.message = null;
    }

    @Nullable
    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.message == null;
    }

    public void setMessage(String str) {
        if (str == null) {
            this.message = "null";
        } else {
            this.message = str;
        }
    }

}
