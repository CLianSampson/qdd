package com.lvgou.qdd.activity.setting;

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
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends BaseActivity {

    private Button backButton;

    private Button commitButton;

    private Button defaultButton;

    private Button signButton;

    private Button payButton;

    private Button shopButton;

    private EditText editText;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment);

        backButton = (Button) findViewById(R.id.CommentActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = (EditText) findViewById(R.id.CommentActivity_edit);

        commitButton = (Button) findViewById(R.id.CommentActivity_commit);
        //设置button背景色为透明
        commitButton.setBackgroundColor(Color.TRANSPARENT);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });

        defaultButton = (Button) findViewById(R.id.CommentActivity_default);
        defaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;

                defaultButton.setBackgroundResource(R.drawable.button_radius_background);
                signButton.setBackgroundResource(R.drawable.button_radius);
                payButton.setBackgroundResource(R.drawable.button_radius);
                shopButton.setBackgroundResource(R.drawable.button_radius);

                //设置字体颜色
                defaultButton.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                signButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                payButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                shopButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
            }
        });


        signButton = (Button) findViewById(R.id.CommentActivity_sign);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;

                defaultButton.setBackgroundResource(R.drawable.button_radius);
                signButton.setBackgroundResource(R.drawable.button_radius_background);
                payButton.setBackgroundResource(R.drawable.button_radius);
                shopButton.setBackgroundResource(R.drawable.button_radius);

                //设置字体颜色
                defaultButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                signButton.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                payButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                shopButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
            }
        });


        shopButton = (Button) findViewById(R.id.CommentActivity_shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;

                defaultButton.setBackgroundResource(R.drawable.button_radius);
                signButton.setBackgroundResource(R.drawable.button_radius);
                payButton.setBackgroundResource(R.drawable.button_radius);
                shopButton.setBackgroundResource(R.drawable.button_radius_background);

                //设置字体颜色
                defaultButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                signButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                payButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                shopButton.setTextColor(getResources().getColorStateList(R.color.systemWhite));
            }
        });


        payButton = (Button) findViewById(R.id.CommentActivity_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type =3;

                defaultButton.setBackgroundResource(R.drawable.button_radius);
                signButton.setBackgroundResource(R.drawable.button_radius);
                payButton.setBackgroundResource(R.drawable.button_radius_background);
                shopButton.setBackgroundResource(R.drawable.button_radius);

                //设置字体颜色
                defaultButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                signButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                payButton.setTextColor(getResources().getColorStateList(R.color.systemWhite));
                shopButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
            }
        });

    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_COMMENT + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("type",type+"");
        map.put("question",editText.getText().toString());

        Logger.getInstance(getApplicationContext()).info("意见反馈参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("意见反馈成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

                ToastUtil.showToast(getApplicationContext(),(String) map.get("info"));
            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("意见反馈列表：" + request.url);
        request.postRequest(getApplicationContext(),map);

    }
}
