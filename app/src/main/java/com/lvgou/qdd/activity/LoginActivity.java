package com.lvgou.qdd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lvgou.qdd.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
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
        String account = accoutEditText.getText().toString();
        String password = passwordEditText.getText().toString();


    }

    @Override
    protected void netRequest() {
        super.netRequest();
    }
}
