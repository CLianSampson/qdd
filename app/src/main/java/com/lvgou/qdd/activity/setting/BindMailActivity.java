package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.graphics.Color;
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
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class BindMailActivity extends BaseActivity {


    private Button backButton;

    private EditText editText;

    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_mail);

        backButton = (Button) findViewById(R.id.BindMailActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        completeButton = (Button) findViewById(R.id.BindMailActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });


        editText = (EditText) findViewById(R.id.BindMailActivity_edit);


    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_BIND_MAIL + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("mail",editText.getText().toString());

        Logger.getInstance(getApplicationContext()).info("绑定邮箱参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("绑定邮箱成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
                JSONObject data = (JSONObject) map.get("data");

                Intent intent = new Intent(getApplicationContext(),BindMailSucessActivity.class);
                intent.putExtra("mail",editText.getText().toString());
                startActivity(intent);

            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("绑定邮箱列表：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }
}
