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
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;
import com.lvgou.qdd.view.SmsCoeView;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserPhoneActivity extends BaseActivity implements SmsCoeView.ISmsCoeView{

    private Button backButton;

    private Button completeButton;

    private EditText phoneInput;

    private SmsCoeView smsCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_user_phone);

        backButton = (Button) findViewById(R.id.ChangeUserPhoneActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.ChangeUserPhoneActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(smsCodeView.getSmsCode())){
                    ToastUtil.showToast(getApplicationContext(),"验证码不能为空");
                }


                netRequest();
            }
        });

        phoneInput = (EditText) findViewById(R.id.ChangeUserPhoneActivity_phone);

        smsCodeView = (SmsCoeView) findViewById(R.id.smsCode);
        smsCodeView.activity = this;
        smsCodeView.callback = this;

    }


    //重新设置手机号
    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_CHANGE_PHONE + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("mobile_code",smsCodeView.getSmsCode());
        map.put("mobile",phoneInput.getText().toString());

        Logger.getInstance(getApplicationContext()).info("重新绑定手机号参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("重新绑定手机号成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
                ToastUtil.showToast(getApplicationContext(),(String) map.get("info"));
            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("重新绑定手机号：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }


    //获取短信验证码
    @Override
    public void getSmsCode(){
        smsCodeView.phone = phoneInput.getText().toString();

//        smsCodeView.post(new Runnable() {
//            @Override
//            public void run() {
//                if (StringUtil.isNullOrBlank(phoneInput.getText().toString())){
//                    ToastUtil.showToast(getApplicationContext(),"手机号不能为空");
//                    return;
//                }
//
//
//                VolleyRequest request = new VolleyRequest();
//
//                request.url = URLConst.URL_SMS  + "mobile=" + phoneInput.getText().toString();
//                request.setCallback(new RequestCallback() {
//                    @Override
//                    public void sucess(String response) {
//                        Logger.getInstance(getApplicationContext()).info("获取短信验证码成功");
//
//                        JSONObject responseData = JSON.parseObject(response,JSONObject.class);
//
//                        ToastUtil.showToast(getApplicationContext(),"短信验证码已发送");
//
//                    }
//
//                    @Override
//                    public void fail(String response) {
//
//                    }
//                });
//                Logger.getInstance(getApplicationContext()).info("获取短信验证码的url : " + request.url);
//
//            }
//        });

    }


}
