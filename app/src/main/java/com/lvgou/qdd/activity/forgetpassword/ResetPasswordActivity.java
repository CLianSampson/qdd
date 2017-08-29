package com.lvgou.qdd.activity.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends BaseActivity {

    private Button backButton;
    private Button nextButton;
    private EditText passwordText;
    private EditText repasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_password);

        backButton = (Button) findViewById(R.id.ResetPasswordActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        passwordText = (EditText) findViewById(R.id.ResetPasswordActivity_password_input);
        repasswordText = (EditText) findViewById(R.id.ResetPasswordActivity_repassword_input);

        nextButton = (Button) findViewById(R.id.ResetPasswordActivity_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });
    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_FORGET_PASSWORD ;
        final Map<String,String> map = new HashMap<>();
        map.put("password",passwordText.getText().toString());
        map.put("repassword",repasswordText.getText().toString());
        map.put("mobile",getIntent().getStringExtra("phone"));
        map.put("mobilecode",getIntent().getStringExtra("smsCode"));
        map.put("verify",getIntent().getStringExtra("verifyCode"));


        Logger.getInstance(getApplicationContext()).info("重设密码参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("重设密码成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
                JSONObject data = (JSONObject) map.get("data");

                startActivity(new Intent(getApplicationContext(),SetPasswordSucessActivity.class));
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("重设密码：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }


}
