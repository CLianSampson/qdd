package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener,RequestCallback {
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

            case R.id.login_account:
                login();
                break;

            case R.id.login_password:
                login();
                break;

            default:
                break;

        }
    }


    private  void  login(){

        netRequest();
    }

    @Override
    protected void netRequest() {
        super.netRequest();
        String account = accoutEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Map<String,String> map = new HashMap<>();
        map.put("username",account);
        map.put("password",password);

        request.url = URLConst.URL_LOGIN;
        request.setCallback(this);
        request.psotRequest(getApplicationContext(),map);
    }

    public  void sucess(Object object){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public  void fail(Object object){

    }
}
