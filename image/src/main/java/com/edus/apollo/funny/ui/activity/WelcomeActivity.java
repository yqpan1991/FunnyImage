package com.edus.apollo.funny.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edus.apollo.funny.R;
import com.edus.apollo.funny.net.model.RegisterResp;
import com.edus.apollo.funny.net.api.EsUserApiHelper;
import com.edus.apollo.funny.utils.EsLog;
import com.edus.apollo.funny.utils.EsSharedPreference;

/**
 * Created by Panda on 2015/10/19.
 */
public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        register();
    }

    private void toMainActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    private void register(){
        String ticket = EsSharedPreference.getTicket();
        EsLog.e(TAG,"TICKET:"+ticket);
        if(TextUtils.isEmpty(ticket)){
            EsUserApiHelper.registerUser(new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    RegisterResp registerResp = JSON.parseObject(s, RegisterResp.class);
                    if (registerResp.isSuc()) {
                        EsSharedPreference.setTicket(registerResp.ticket);
                        toMainActivity();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), registerResp.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    EsLog.e("WelcomeActivity", s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    EsLog.e("WelcomeActivity", volleyError.toString());
                    finish();
                }
            });
        }else{
            toMainActivity();
            finish();
        }


    }
}
