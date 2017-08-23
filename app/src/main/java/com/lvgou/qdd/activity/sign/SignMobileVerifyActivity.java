package com.lvgou.qdd.activity.sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;
import com.lvgou.qdd.view.SmsCoeView;

import java.util.HashMap;
import java.util.Map;

public class SignMobileVerifyActivity extends BaseActivity implements SmsCoeView.ISmsCoeView{

    private Button backButton;

    private TextView phoneText;

    private String phone;

    private String signId;

    private String signStatus;

    private Button confirmButton;

    private Button cancelButton;

    private SmsCoeView smsCoeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_mobile_verify);

        signId = getIntent().getStringExtra("signId");
        signStatus = getIntent().getStringExtra("signStatus");

        backButton = (Button) findViewById(R.id.SignMobileVerifyActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        phoneText = (TextView) findViewById(R.id.SignMobileVerifyActivity_phone);
        phone = getIntent().getStringExtra("phone");
        setPhoneText(phone);

        confirmButton = (Button) findViewById(R.id.SignMobileVerifyActivity_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(smsCoeView.getSmsCode())){
                    ToastUtil.showToast(getApplicationContext(),"验证码不能为空");
                    return;
                }

                netRequest();
            }
        });

        cancelButton = (Button) findViewById(R.id.SignMobileVerifyActivity_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        smsCoeView = (SmsCoeView) findViewById(R.id.SignMobileVerifyActivity_smsCode);
        smsCoeView.activity = this;
        smsCoeView.callback = this;

    }



    public void  setPhoneText(String account){
        if (account == null || account.equals(" ")){
            return;
        }
        String one = account.substring(0,3);
        String two = account.substring(3,7);
        String three = account.substring(7,account.length());

        String string = one + " " + two + " " + three;
        this.phoneText.setText(string);
    }


    @Override
    public void getSmsCode() {
        smsCoeView.phone = phone;
        smsCoeView.urlSignGetSmsCode = URLConst.URL_SIGN_GET_SMS_CODE + TokenUtil.token;
    }

    //验证手机验证码
    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_VERIFY_MOBILE_CODE + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("mobile_code",smsCoeView.getSmsCode());
        map.put("id",signId);

        Logger.getInstance(getApplicationContext()).info("验证手机验证码参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("验证手机验证码成功");

                signSignature();

            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("验证手机验证码：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }


    //签合同
    private void signSignature(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_SIGN_SIGNATURE + TokenUtil.token + "/id/" + signId + "/status/" + signStatus;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("签合同成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

                String mealTimes = (String) map.get("data");

                Intent intent = new Intent(getApplicationContext(),SignSucessActivity.class);
                intent.putExtra("remainTimes",mealTimes);
                startActivity(intent);
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("签合同的url is ："  + request.url);
        request.getRequest(getApplicationContext());
    }


}
