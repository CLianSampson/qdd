package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.forgetpassword.ForgetPasswordActivity;
import com.lvgou.qdd.activity.register.RegisterActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StorageUtil;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private  static String TAG = "LoginActivity";

    private  EditText accoutEditText;
    private  EditText passwordEditText;
    private  Button loginButton;
    private  Button registerButton;
    private  Button forgetpasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void childImpl(Bundle savedInstanceState){
        setContentView(R.layout.activity_login);
        setVIew();
    }

    private  void  setVIew(){
        accoutEditText = (EditText) findViewById(R.id.login_account);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        forgetpasswordButton = (Button)findViewById(R.id.forgetPasswordButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgetpasswordButton.setOnClickListener(this);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginButton:
                login();
                break;

            case R.id.registerButton:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;

            case R.id.forgetPasswordButton:
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                break;

            default:
                break;

        }
    }


    private void login(){
        netRequest();
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        final Map<String,String> map = new HashMap<>();

        map.put("username","18771098004");
        map.put("password","1234567");

//        map.put("username","wangnanqiao@qiandd.com");
//        map.put("password","lingxi0502");

        final String account = accoutEditText.getText().toString();
        String password = passwordEditText.getText().toString();

//        map.put("username",account);
//        map.put("password",password);


        request.url = URLConst.URL_LOGIN;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("登录返回");
                Map<String,Object> responseMap = JSON.parseObject(response,new HashMap<String,String>().getClass());

                Map<String,String> data = (Map<String, String>) responseMap.get("data");

                String token = data.get("token");

                Logger.getInstance(getApplicationContext()).info("token is :"  + token);

                StorageUtil.storeData(getApplicationContext(),StorageUtil.TOKEN,token);
                StorageUtil.storeData(getApplicationContext(),StorageUtil.PHONE,"18771098004");
                TokenUtil.token = token;

                if (StringUtil.isPhoneNum(account)){
                    StorageUtil.storeData(getApplicationContext(),StorageUtil.ACCOUNT_FLAG,TokenUtil.USER_ACCOUNT);
                    TokenUtil.account_flag = TokenUtil.USER_ACCOUNT;
                }else {
                    StorageUtil.storeData(getApplicationContext(),StorageUtil.ACCOUNT_FLAG,TokenUtil.ENTERPRISE_ACCOUNT);
                    TokenUtil.account_flag = TokenUtil.ENTERPRISE_ACCOUNT;
                }

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void fail(String object) {

            }
        });
        request.postRequest(getApplicationContext(),map);
    }


}
