package com.lvgou.qdd.activity.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Constant;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;
import com.lvgou.qdd.view.SmsCoeView;

import java.util.HashMap;
import java.util.Map;

public class UserVerifyActivity extends BaseActivity implements SmsCoeView.ISmsCoeView{

    private LinearLayout praentView;

    private Button backButton;

    private EditText nameText;

    private EditText idNumtext;

    private EditText bankNumText;

    private EditText phoneText;

    private SmsCoeView smsCoeView;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_verify);

        //让键盘回收
        praentView = (LinearLayout) findViewById(R.id.UserVerifyActivity_parent);
        praentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        backButton = (Button) findViewById(R.id.UserVerifyActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameText = (EditText) findViewById(R.id.UserVerifyActivity_name_input);
        idNumtext = (EditText) findViewById(R.id.UserVerifyActivity_idNum__input);
        bankNumText = (EditText) findViewById(R.id.UserVerifyActivity_bank__input);
        phoneText = (EditText) findViewById(R.id.UserVerifyActivity_phone__input);

        smsCoeView = (SmsCoeView) findViewById(R.id.UserVerifyActivity_smsCode);
        smsCoeView.activity = this;
        smsCoeView.callback = this;


        nextButton = (Button) findViewById(R.id.UserVerifyActivity_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneText.getText().toString();
                String smsCode = smsCoeView.getSmsCode();
                String name = nameText.getText().toString();
                String bank = bankNumText.getText().toString();
                String idNum = idNumtext.getText().toString();


                if (StringUtil.isNullOrBlank(phone)
                        || StringUtil.isNullOrBlank(smsCode)
                        || StringUtil.isNullOrBlank(name)
                        || StringUtil.isNullOrBlank(bank)
                        || StringUtil.isNullOrBlank(idNum)){

                    ToastUtil.showToast(getApplicationContext(),"输入内容不能为空");
                }


                netRequest();

            }
        });


    }

    @Override
    public void getSmsCode() {
        smsCoeView.phone = phoneText.getText().toString();
    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_USER_VERIFY + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();
        map.put("name",nameText.getText().toString());
        map.put("sfz",idNumtext.getText().toString());
        map.put("bankid",bankNumText.getText().toString());
        map.put("mobile",phoneText.getText().toString());
        map.put("mobilecode",smsCoeView.getSmsCode());


        Logger.getInstance(getApplicationContext()).info("个人认证参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("个人认证成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
//                JSONObject data = (JSONObject) map.get("data");

               ToastUtil.showToast(getApplicationContext(),"个人认证成功");

                Intent intent = new Intent();
                setResult(Constant.GO_HOME_ACTIVITY_FROM_VERIFY_ACTIVITY,intent);
                finish();

            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("个人认证：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }





}
