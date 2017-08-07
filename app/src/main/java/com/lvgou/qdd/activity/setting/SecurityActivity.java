package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

public class SecurityActivity extends BaseActivity{

    private Button backButton;

    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security);

        backButton = (Button) findViewById(R.id.SecurityActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetButton = (Button) findViewById(R.id.SecurityActivity_reset);
        resetButton.setBackgroundColor(Color.TRANSPARENT);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_GET_ACCOUNT_INFO + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("安全设置获取个人信息成功");

                JSONObject responseData = JSON.parseObject(response,JSONObject.class);

                JSONObject data = (JSONObject) responseData.get("data");

                String idName = (String) data.get("idname");

                Intent intent = new Intent(getApplicationContext(),SetPasswordActivity.class);
                intent.putExtra("idName",idName);
                startActivity(intent);


            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("安全设置获取个人信息的url is ："  + request.url);
        request.getRequest(getApplicationContext());
    }
}
